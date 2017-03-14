package com.yingjun.ssm.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import com.google.common.base.Joiner;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Lists;
import com.google.common.collect.Multiset;
import com.google.common.collect.Sets;
import com.yingjun.ssm.cache.RedisCache;
import com.yingjun.ssm.entity.User;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.*;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.shiro.subject.support.SubjectThreadState;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.LocalDate;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;

/**
 * Created by kongyunhui on 16/10/20.
 */
public class Test {
    public static void main(String[] args) throws Exception{
//        // 统计0~n中自然数出现的次数
//        Multiset multiset = statNum(11);
//        System.out.println(multiset);
//
//        // 验证list!=null && list.size()>0
//        List list = Lists.newArrayList(1);
//        list = Validate.notEmpty(list);
//        System.out.println(list.get(0));
//
//        // 交集
//        Sets.SetView<Integer> intersection = Sets.intersection(Sets.newHashSet(1, 2, 3), Sets.newHashSet(2, 3, 4));
//        System.out.println(intersection);
//        Collection intersection1 = CollectionUtils.intersection(Sets.newHashSet(1, 2, 3), Sets.newHashSet(2, 3, 4));
//        System.out.println(intersection1);
//
//        // 截取where后面的字符串
//        String sql = "select * from tbl_user where 1=1";
//        String where = StringUtils.substringAfter(sql, "where");
//        System.out.println(where);
//
//        int pos = sql.indexOf("where");
//        System.out.println(sql.substring(pos+"where".length()));
//
//        String[] wheres = sql.split("where");
//        if(wheres.length>1) System.out.println(wheres[1]);
//
//        // 字符串类型转换
//        boolean number = NumberUtils.isNumber("-1");
//        System.out.println(number);
//
//        double l = NumberUtils.toDouble("11.3");
//        System.out.println(l);
//        String s=null;
//        l = s==null?0.0:Double.parseDouble(s);
//        System.out.println(l);
//
//        // 随机数字字母组合
//        String s1 = RandomStringUtils.randomAlphanumeric(4);
//        System.out.println(s1);
//
//        MapUtils.getString(new HashMap(), "name", "");
//
//        NumberUtils.toLong(new HashMap<String,String>().get("quantity"));
//
//        System.out.println(new Date(1362573503891l));

//        String className = "com.yingjun.ssm.service.impl.UserServiceImpl";
//        // className中含有'UserService'中的任意字符 --> true
//        if(StringUtils.containsAny(className, "UserService")){
//            System.out.println("yes");
//        }
//        // className中含有'UserService'字符串 --> true
//        if(StringUtils.contains(className, "UserService")){
//            System.out.println("yes");
//        }
//
//        // 等价于strs.contains(str)
//        System.out.println(ArrayUtils.contains(new String[]{"createUser", "updateUser", "deleteUser", "changePassword"}, "updateUser"));


//        ArrayList<String> list = Lists.newArrayList("");
//        System.out.println(CollectionUtils.isEmpty(list));
//        String[] roleIdStrs = "".split(",");
//        System.out.println(roleIdStrs[0] + "");
//
//        if(CollectionUtils.isEmpty(list)) {
//            System.out.println("#");
//        }
//        StringBuilder s = new StringBuilder();
//        for(int i=0; i<list.size(); i++) {
//            if(i>0){
//                s.append(",");
//            }
//            s.append(list.get(i));
//        }
//        System.out.println(s.toString()+"#");

//        String jsonParams = "{\"title\":\"te\",\"id\":35,\"product_type\":0,\"product_img\":\"\\/imgs\\/20151117\\/14476939460988.png\",\"swiper_img\":\"[{\\\"type\\\":\\\"img\\\",\\\"url\\\":\\\"\\/imgs\\/20151117\\/14476939539368.png\\\"},{\\\"type\\\":\\\"img\\\",\\\"url\\\":\\\"\\/imgs\\/20160602\\/14648502400375.png\\\"},{\\\"type\\\":\\\"img\\\",\\\"url\\\":\\\"\\/imgs\\/20160602\\/14648502400375.png\\\"},{\\\"type\\\":\\\"img\\\",\\\"url\\\":\\\"\\/imgs\\/20160527\\/14643379794204.jpg\\\"},{\\\"type\\\":\\\"img\\\",\\\"url\\\":\\\"\\/imgs\\/20160527\\/14643379794204.jpg\\\"},{\\\"type\\\":\\\"img\\\",\\\"url\\\":\\\"\\/imgs\\/20160527\\/14643379794204.jpg\\\"}]\",\"detail_img\":\"[{\\\"type\\\":\\\"img\\\",\\\"url\\\":\\\"\\/imgs\\/20151117\\/14476940269544.png\\\"},{\\\"type\\\":\\\"img\\\",\\\"url\\\":\\\"\\/imgs\\/20151117\\/14476940266484.png\\\"},{\\\"type\\\":\\\"img\\\",\\\"url\\\":\\\"\\/imgs\\/20151117\\/14476940250971.png\\\"},{\\\"type\\\":\\\"img\\\",\\\"url\\\":\\\"\\/imgs\\/20151117\\/14476940260677.png\\\"},{\\\"type\\\":\\\"img\\\",\\\"url\\\":\\\"\\/imgs\\/20151117\\/14476940261448.png\\\"},{\\\"type\\\":\\\"img\\\",\\\"url\\\":\\\"\\/imgs\\/20151117\\/14476940275960.png\\\"},{\\\"type\\\":\\\"img\\\",\\\"url\\\":\\\"\\/imgs\\/20151117\\/14476940275960.png\\\"},{\\\"type\\\":\\\"img\\\",\\\"url\\\":\\\"\\/imgs\\/20151117\\/14476940279833.png\\\"},{\\\"type\\\":\\\"img\\\",\\\"url\\\":\\\"\\/imgs\\/20160602\\/14648503190922.jpg\\\"}]\",\"is_show\":\"0\",\"is_sale\":\"0\",\"support_pay_type\":24,\"deliver_price\":0,\"config\":\"test\",\"created_at\":1447694431,\"updated_at\":1479883027,\"sale_mark\":1,\"sort\":0,\"store_id\":27,\"platform\":1}";
//        JSONObject jsonObject = JSON.parseObject(jsonParams);
//        System.out.println(jsonObject.toJSONString());

//        DateTime start = DateTime.now();
//        DateTime end = new DateTime(2016, 12, 8, 0 , 0, 0);
//        System.out.println(start);
//        System.out.println(end);
//        int days = Days.daysBetween(start, end).getDays();
//        System.out.println(days);

//        String str = "List com.yingjun.ssm.dao.UserDao.findAll()";
//        String[] split = str.split("\\s");
//        System.out.println(split[1]);
//        str = split[1];
//        String className = str.substring(0, str.lastIndexOf("."));
//        System.out.println(className);

//        System.out.println(Joiner.on("|").join(RedisCache.CAHCENAME, RedisCache.CAHCETIME));
//
//        Object l = new Long(1l);
//        System.out.println(l.toString());
//        Object o = new User("kong", "123", "123");
//        System.out.println(o.toString());

//        LinkedList stack = new LinkedList();
//        stack.push("2");
//        stack.push("1");
//        stack.push("2");
//        System.out.println(stack);

//        futureTaskFun();
//        threadFun();

//        System.out.println(new DateTime(2016,12,29,0,0,0).getMillis());

//        ScheduledExecutorPoolFun2();
//        String str="{\n" +
//                "\t\"id\":1,\n" +
//                "\t\"username\":\"kong\",\n" +
//                "\t\"password\":\"123\",\n" +
//                "\t\"salt\":\"123\"\n" +
//                "}";
//        User s = JSONObject.parseObject(str, User.class);
//        System.out.println(s);

        List<String> chainNames = new ArrayList<String>();
        chainNames.add("/**");
        chainNames.add("/static/**");

//        Set<String> anonChainNames = new HashSet<>();
//        anonChainNames.add("/static/**");
//        anonChainNames.add("/register/**");
//        anonChainNames.add("/api/**");

        HashSet<String> anonChainNames = Sets.newHashSet("/static/**", "/register/**", "/api/**");

        Set<String> chainNameSet = new HashSet<String>();
        chainNameSet.addAll(chainNames);


        Sets.SetView<String> intersection = Sets.intersection(chainNameSet, anonChainNames);
        if(intersection!=null && intersection.size()>0){
            System.out.println(intersection);
        }
    }

    private static void ScheduledExecutorPoolFun1() throws Exception{
        ScheduledExecutorService mExecutorService = Executors.newScheduledThreadPool(3);
        FutureTask<String> stringFutureTask = new FutureTask<>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                try {
                    Thread.sleep(2000);
                } catch (Exception e) {
                }
                return Thread.currentThread().getName();
            }
        });
        mExecutorService.scheduleAtFixedRate(stringFutureTask, 1, 2, TimeUnit.SECONDS);
        System.out.println(stringFutureTask.get());
        // ExecutorService停止接收任务，但是已经提交的任务继续执行，执行完后ExecutorService正式从关闭状态->终止状态
        // 从结果来看，仅仅打印一次threadName，符合预期！
        mExecutorService.shutdown();
    }

    private static void ScheduledExecutorPoolFun2(){
        /**
         * ScheduledExecutorService extends ExecutorService
         * 专门定义了关于ScheduleExecutorPool的api
         */
        ScheduledExecutorService mExecutorService = Executors.newScheduledThreadPool(3);
        mExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
                try{
                    Thread.sleep(2000);
                }catch(Exception e){}
            }
        }, 1, 5, TimeUnit.SECONDS);
//        mExecutorService.shutdown(); // 定期执行。在提交之前，shutdown就执行了，因此没有线程执行。
    }

    private static void futureTaskFun() throws Exception{
        FutureTask futureTask = new FutureTask(new Callable() {
            @Override
            public Object call() throws Exception {
                Thread.sleep(3000);
                System.out.println("futureTask finish");
                return 10;
            }
        });
        ExecutorService mExecutor = Executors.newSingleThreadExecutor();
        mExecutor.submit(futureTask);
        System.out.println(futureTask.get());
        System.out.println("i am a futureTask test!");
        mExecutor.shutdown();
    }

    private static void threadFun(){
        try {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(3000);
                    }catch(Exception e){}
                    System.out.println("thread finish");
                }
            });
            thread.start();
//            thread.join(); // 使用FutureTask，也就是Future，他的get就可以阻塞线程了
            System.out.println("i am a thread test!");
        } catch(Exception e){};
    }

    /**
     *
     * 一本书的页码从自然数1 开始顺序编码直到自然数n。书的页码按照通常的习惯编排，每个页码都不含多余的前导数字0。
     * 例如，第6 页用数字6 表示，而不是06 或006 等。
     * 数字计数问题要求对给定书的总页码n，计算出书的全部页码中分别用到多少次数字0，1，2，…，9。
     *
     * @param n 总页码
     * @return
     */
    public static Multiset statNum(int n){
        List<Integer> words = new ArrayList<>();
        for(int i=0; i<=n; i++)
            // 递归分解处理
            words.add(factorization(i, words));

        Multiset<Integer> multiset = HashMultiset.create(words);
        return multiset;
    }

    public static int factorization(int num, List<Integer> words){
        if(num<10)
            return num;
        else {
            int lastNum = num % 10;
            words.add(lastNum);
            return factorization(num / 10, words);
        }
    }
}
