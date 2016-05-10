package com.nbm.core.modeldriven;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum ModelUtils
{

        INSTANCE;

        private final static Logger log = LoggerFactory.getLogger(ModelUtils.class);

        public List<Field> getFields(Class<? extends Model> modelClass)
        {
                List<Field> result = new ArrayList<>();

                Class<? extends Model> claz = modelClass;
                while (true)
                {
                        result.addAll(handleAClass(claz));

                        if (claz.equals(Model.class))
                        {
                                break;
                        }

                        claz = (Class<? extends Model>) claz.getSuperclass();
                }

                Collections.sort(result, new Comparator<Field>()
                {

                        @Override
                        public int compare(Field o1, Field o2)
                        {
                                // 主键一定排列在第一个
                                if (o1.isPk())
                                        return -1;

                                if (o2.isPk())
                                        return 1;
                                return 0;
                        }
                });

                return result;

        }

        private List<Field> handleAClass(Class<? extends Model> modelClass)
        {
                List<Field> result = new ArrayList<>();

                // for (PropertyDescriptor descriptor : PropertyUtils
                // .getPropertyDescriptors(modelClass))

                for (java.lang.reflect.Field f : modelClass.getDeclaredFields())
                {

                        // 如果该property不属于Model接口的子类，则不处理
                        if (!Model.class.isAssignableFrom(f.getDeclaringClass()))
                        {
                                continue;
                        }

                        // log.debug(f.getName());

                        // 不处理静态变量
                        if (Modifier.isStatic(f.getModifiers()))
                        {
                                continue;
                        }
                        
                        if( "proxy".equals(f.getName()))
                        {
                                continue;
                        }

                        String upper = f.getName().substring(0, 1).toUpperCase()
                                        + f.getName().substring(1);

                        // // 必须有get、set方法，才是做db属性
                        // if (descriptor.getReadMethod() == null
                        // || descriptor.getWriteMethod() == null)
                        // {
                        // continue;
                        // }


                        result.add(new Field(f));
                }

                return result;
        }

        public static void main(String[] args)
        {
                // System.out.println("asfasdf");
                // for( java.lang.reflect.Field f
                // :ModelDrivenTestModel.class.getDeclaredFields())
                // {
                // System.out.println(f);
                // }

                // System.out.println(Model.class.getSuperclass());

//                log.debug(INSTANCE.getFields(PregnancyCheckRecord.class).toString());
        }

}
