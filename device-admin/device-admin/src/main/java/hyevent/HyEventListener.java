package hyevent;

import com.gz.common.StockInService;
import com.gz.common.StockService;
import com.jfinal.kit.Kv;

import net.dreamlu.event.core.EventListener;

public class HyEventListener {
    @EventListener
    public void listenStockInEvent(StockInEvent event) {
        Kv kv=event.getSource();
       if("receive".equals(kv.getStr("flag"))){
           //1.入库
           StockService.getService().saveStock(kv.getStr("billNo"));
           //2.完成stockin状态更新
           StockInService.getService().updateStatus(kv.getStr("billNo"));
       } 
       
    }
    
} 