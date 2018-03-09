/**
 * Package: com.lbc.refresh.polling
 * Description: 
 */
package com.lbc.refresh.polling;

import com.lbc.refresh.StatusMonitor;
import com.lbc.refresh.StatusAcquirer;

/**
 * Description:  
 * Date: 2018年3月9日 下午5:16:50
 * @author wufenyun 
 */
public interface PollingMonitor extends StatusMonitor {
    
    void setStatusAcquirer(StatusAcquirer sAcquirer);
}
