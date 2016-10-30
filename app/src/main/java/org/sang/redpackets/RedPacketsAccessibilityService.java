package org.sang.redpackets;

import android.accessibilityservice.AccessibilityService;
import android.app.Notification;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by 王松 on 2016/10/27.
 */

public class RedPacketsAccessibilityService extends AccessibilityService {

    private Set<Integer> currentScreenRedPackets = new HashSet<>();
    private int lastFirstVisiableRedPackets;

    @Override
    public void onCreate() {
        super.onCreate();
        Notification.Builder builder = new Notification.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher).setContentTitle("红包神器运行中");
        startForeground(1, builder.build());
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (event.getSource() != null) {
            AccessibilityNodeInfo rootInActiveWindow = getRootInActiveWindow();
            if (rootInActiveWindow != null) {
                if (event.getEventType()==AccessibilityEvent.TYPE_VIEW_SCROLLED) {
                    //抢红包
                    List<AccessibilityNodeInfo> list = rootInActiveWindow.findAccessibilityNodeInfosByText("领取红包");
                    if (list != null && list.size() > 0) {
                        AccessibilityNodeInfo firstNode = list.get(0);
                        if (lastFirstVisiableRedPackets != firstNode.hashCode()) {
                            currentScreenRedPackets.remove(lastFirstVisiableRedPackets);
                            lastFirstVisiableRedPackets = firstNode.hashCode();
                        }
                        for (int i = list.size() - 1; i > -1; i--) {
                            AccessibilityNodeInfo nodeInfo = list.get(i);
                            if (!currentScreenRedPackets.contains(nodeInfo.hashCode())) {
                                AccessibilityNodeInfo parent = nodeInfo.getParent();
                                if (parent != null) {
                                    parent.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                                    currentScreenRedPackets.add(nodeInfo.hashCode());
                                    break;
                                }
                            }
                        }
                    }
                }
                //拆红包
                List<AccessibilityNodeInfo> nodeInfoList = rootInActiveWindow.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/bdg");
                if (nodeInfoList != null && nodeInfoList.size() > 0) {
                    for (AccessibilityNodeInfo accessibilityNodeInfo : nodeInfoList) {
                        accessibilityNodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    }
                }
            }
        }
    }

    @Override
    public void onInterrupt() {

    }
}
