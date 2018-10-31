package hyevent;

import com.jfinal.kit.Kv;

import net.dreamlu.event.core.ApplicationEvent;

public class StockInEvent extends ApplicationEvent<Kv> {
    private static final long serialVersionUID = 6994987952247306131L;
    public StockInEvent(Kv source){
        super((Kv)source);
    }

}