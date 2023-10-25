package com.demo.admin.schedule;

import org.quartz.*;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyAccessorFactory;

public abstract class QuartzJobBean implements Job {

    /**
     * This implementation applies the passed-in job data map as bean property
     * values, and delegates to {@code executeInternal} afterwards.
     * @see #executeInternal
     */
    @Override
    public final void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            // 将当前对象包装为BeanWrapper
            BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(this);
            // 设置属性
            MutablePropertyValues pvs = new MutablePropertyValues();
            pvs.addPropertyValues(context.getScheduler().getContext());
            pvs.addPropertyValues(context.getMergedJobDataMap());
            bw.setPropertyValues(pvs, true);
        }
        catch (SchedulerException ex) {
            throw new JobExecutionException(ex);
        }
        // 子类实现该方法
        executeInternal(context);
    }

    /**
     * Execute the actual job. The job data map will already have been
     * applied as bean property values by execute. The contract is
     * exactly the same as for the standard Quartz execute method.
     * @see #execute
     */
    protected abstract void executeInternal(JobExecutionContext context) throws JobExecutionException;

}
