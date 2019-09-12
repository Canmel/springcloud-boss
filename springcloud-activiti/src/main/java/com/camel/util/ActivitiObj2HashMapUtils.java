package com.camel.util;

import org.activiti.engine.repository.ProcessDefinition;

import java.util.HashMap;
import java.util.Map;

/**
 *
 *                 ___====-_  _-====___
 *           _--^^^#####//      \\#####^^^--_
 *        _-^##########// (    ) \\##########^-_
 *       -############//  |\^^/|  \\############-
 *     _/############//   (@::@)   \\############\_
 *    /#############((     \\//     ))#############\
 *   -###############\\    (oo)    //###############-
 *  -#################\\  / VV \  //#################-
 * -###################\\/      \//###################-
 *_#/|##########/\######(   /\   )######/\##########|\#_
 *|/ |#/\#/\#/\/  \#/\##\  |  |  /##/\#/  \/\#/\#/\#| \|
 *`  |/  V  V  `   V  \#\| |  | |/#/  V   '  V  V  \|  '
 *   `   `  `      `   / | |  | | \   '      '  '   '
 *                    (  | |  | |  )
 *                   __\ | |  | | /__
 *                  (vvv(VVV)(VVV)vvv)
 * <ACTIVITI对象转换HASHMAP工具类>
 * @author baily
 * @since 1.0
 * @date 2019/7/7
 **/
public class ActivitiObj2HashMapUtils {
    private final static ActivitiObj2HashMapUtils INSTANCE = new ActivitiObj2HashMapUtils();

    public ActivitiObj2HashMapUtils() {
    }

    public static ActivitiObj2HashMapUtils getInstance(){return INSTANCE;}

    public Map<String, Object> processDefinition2Map(ProcessDefinition definition){
        Map<String, Object> result = new HashMap<>(16);

        result.put("key", definition.getKey());
        result.put("version", definition.getVersion());
        result.put("deploymentId", definition.getDeploymentId());
        result.put("resourceName", definition.getResourceName());
        result.put("diagramResourceName", definition.getDiagramResourceName());
        result.put("id", definition.getId());
        result.put("description", definition.getDescription());
        return result;
    }
 }
