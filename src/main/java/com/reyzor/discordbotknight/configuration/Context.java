package com.reyzor.discordbotknight.configuration;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * @author Reyzor
 * @version 1.0
 * @since 26.05.2018
 */

public class Context {
    private volatile static Context context;
    private ApplicationContext applicationContext;
    private Configuration configuration;

    private Context(){}

    public static Context getInstance()
    {
        if (context == null)
        {
            synchronized (Context.class)
            {
                if (context == null) {
                    context = new Context();
                }
            }
        }
        return context;
    }

    public ApplicationContext initContext(Class clazz)
    {
        if (applicationContext == null && Arrays.asList(clazz.getAnnotations()).stream().anyMatch(annotation -> annotation instanceof Configuration))
        {
            return applicationContext = new AnnotationConfigApplicationContext(clazz);
        }
        return null;
    }

    public ApplicationContext getApplicationContext()
    {
        return applicationContext;
    }
}
