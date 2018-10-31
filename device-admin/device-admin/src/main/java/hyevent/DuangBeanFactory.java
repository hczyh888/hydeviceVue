package hyevent;

import com.jfinal.aop.Duang;

import net.dreamlu.event.core.IBeanFactory;

public class DuangBeanFactory implements IBeanFactory{
    @Override
    public <T> T getBean(Class<T> requiredType) throws Exception {
        return Duang.duang(requiredType);
    }
}
