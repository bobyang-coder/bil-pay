package com.bob.workflow.activiti;

import org.activiti.bpmn.model.*;
import org.activiti.bpmn.model.Process;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by bob on 2017/9/27.
 *
 * @author bob   <bobyang_coder@163.com>
 * @version v1.0
 * @since 2017/9/27
 */
public class ActivitiTest {

    private static Map<String, Map<String, Object>> PROCESS_NODE_MAP = null;

    public Map getFlowNodeRel(String processDefKey) {
        if (null == PROCESS_NODE_MAP) {
            PROCESS_NODE_MAP = new HashMap<String, Map<String, Object>>();
        }
        if (null == PROCESS_NODE_MAP.get(processDefKey)) {
            ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
            List<Process> processList = engine.getRepositoryService().getBpmnModel(processDefKey).getProcesses();
            Process process = null;
            if (processList.size() > 0) {
                process = processList.get(0);
                Map relationMap = new HashMap<String, Integer>();
                setFlowNodeRel(process, relationMap);
                PROCESS_NODE_MAP.put(processDefKey, relationMap);
            }
        }
        return PROCESS_NODE_MAP.get(processDefKey);
    }

    //根据process往relationMap里写入UserTask之间的前后关系，从start开始遍历节点，不循环
    private void setFlowNodeRel(Process process, Map relationMap) {
        Iterator<FlowElement> iterator = process.getFlowElements().iterator();
        HashMap nodeMap = new HashMap<String, FlowElement>();
        setFlowNodeMap(process, nodeMap);
        //获取start节点
        StartEvent startEvent = (StartEvent) nodeMap.get("startEvent");
        //处理节点关系
        setFlowNodeRel("", "startEvent", nodeMap, relationMap, 1);
    }

    //根据节点ID设置关系
    private void setFlowNodeRel(String parentKey, String nodeId, Map<String, FlowElement> nodeMap, Map relationMap, int level) {
        FlowElement element = nodeMap.get(nodeId);
        if (element instanceof UserTask) {
            relationMap.put(nodeId, element);
        }
        if (!(element instanceof FlowNode)) {
            return;
        }

        List<SequenceFlow> outFlows = ((FlowNode) element).getOutgoingFlows();
        if (outFlows.size() == 0) {
            return;
        }

        //采用深度遍历
        for (int index = 0; index < outFlows.size(); index++) {
            SequenceFlow tmpSequence = outFlows.get(index);
            String target = tmpSequence.getTargetRef();
            String key = nodeId + "#" + target;

            String loopKey = target + "#" + nodeId;

            FlowElement tmpElement = nodeMap.get(target);
            //只是源和目标都是 用户任务 才写入
            if (relationMap.containsKey(loopKey) || relationMap.containsKey(key)) {
                continue;
            }
            if (tmpElement instanceof UserTask && element instanceof UserTask) {
                setRelMapValue(relationMap, parentKey, target, level);
            }
            //递归找下一个任务的关系
            setFlowNodeRel(parentKey + "#" + target, target, nodeMap, relationMap, level + 1);
        }
    }

    private void setRelMapValue(Map relationMap, String parentKey, String nodeKey, int level) {
        if (parentKey.length() > 0) {
            String[] parentArr = parentKey.split("#");
            for (String parent : parentArr) {
                if (StringUtils.isNotEmpty(parent)) {
                    String key = parent + "#" + nodeKey;
                    String loopKey = nodeKey + "#" + parent;
                    if (relationMap.containsKey(loopKey) || relationMap.containsKey(key)) {
                        continue;
                    } else {
                        relationMap.put(key, level);
                    }
                }
            }
        }
    }

    //把流程节点设置到map属性里
    private void setFlowNodeMap(Process process, Map<String, FlowElement> nodeMap) {
        Iterator<FlowElement> iterator = process.getFlowElements().iterator();
        while (iterator.hasNext()) {
            FlowElement element = iterator.next();
            if (element instanceof StartEvent) {
                nodeMap.put("startEvent", element);
            } else {
                nodeMap.put(element.getId(), element);
            }
        }
    }
}
