package org.langke.java8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Stack;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * 
 * @author langke http://www.cnblogs.com/duanxz/archive/2012/07/19/2598726.html
 *         https://www.ibm.com/developerworks/cn/java/j-lo-java8streamapi/
 *         http://www.ibm.com/developerworks/cn/java/j-lo-jdk8newfeature/index.html
 *         1.1 为什么加入 集合的流式操作
 * 
 *         JDK8 的Stream 是一个受到 函数式编程 和
 *         多核时代影响而产生的东西。很多时候我们需要到底层返回数据，上层再对数据进行遍历，进行一些数据统计，但是之前的Java API
 *         中很少有这种方法，这就需要我们自己来 Iterator 来遍历，如果JDK 能够为我们提供一些这种方法，并且能够为我们优化就好了。
 *         所以JDK8加入 了
 *         java.util.stream包，实现了集合的流式操作，流式操作包括集合的过滤，排序，映射等功能。根据流的操作性，又可以分为 串行流 和
 *         并行流。根据操作返回的结果不同，流式操作又分为中间操作和最终操作。大大方便了我们对于集合的操作。 最终操作：返回一特定类型的结果。
 *         中间操作：返回流本身。 1.2 什么是 流 Stream 不是 集合元素，也不是数据结构，它相当于一个 高级版本的
 *         Iterator，不可以重复遍历里面的数据，像水一样，流过了就一去不复返。它和普通的 Iterator 不同的是，它可以并行遍历，普通的
 *         Iterator 只能是串行，在一个线程中执行。
 * 
 *         二. 串行流和并行流： 串行流操作在一个线程中依次完成。并行流在多个线程中完成，主要利用了 JDK7 的 Fork/Join
 *         框架来拆分任务和加速处理。相比串行流，并行流可以很大程度提高程序的效率。
 * 
 *         三. 中间操作 和 最终操作 中间操作： filter()： 对元素进行过滤 sorted()：对元素排序 map()：元素映射
 *         distinct()：去除重复的元素 最终操作： forEach()：遍历每个元素。 reduce()：把Stream
 *         元素组合起来。例如，字符串拼接，数值的 sum，min，max ，average 都是特殊的 reduce。
 *         collect()：返回一个新的集合。 min()：找到最小值。 max()：找到最大值。
 *
 *         Ps1：除了上面介绍的方法，还有如下方法： Intermediate： map (mapToInt, flatMap 等)、
 *         filter、 distinct、 sorted、 peek、 limit、 skip、 parallel、 sequential、
 *         unordered Terminal： forEach、 forEachOrdered、 toArray、 reduce、
 *         collect、 min、 max、 count、 anyMatch、 allMatch、 noneMatch、 findFirst、
 *         findAny、 iterator Short-circuiting： anyMatch、 allMatch、 noneMatch、
 *         findFirst、 findAny、 limit
 * 
 *         有多种方式生成 Stream Source： 从 Collection 和数组 Collection.stream()
 *         Collection.parallelStream() Arrays.stream(T array) or Stream.of() 从
 *         BufferedReader java.io.BufferedReader.lines() 静态工厂
 *         java.util.stream.IntStream.range() java.nio.file.Files.walk() 自己构建
 *         java.util.Spliterator 其它 Random.ints() BitSet.stream()
 *         Pattern.splitAsStream(java.lang.CharSequence) JarFile.stream()
 * 
 *         流的操作类型分为两种： Intermediate：一个流可以后面跟随零个或多个 intermediate
 *         操作。其目的主要是打开流，做出某种程度的数据映射
 *         /过滤，然后返回一个新的流，交给下一个操作使用。这类操作都是惰性化的（lazy），就是说，仅仅调用到这类方法，并没有真正开始流的遍历。
 *         Terminal：一个流只能有一个 terminal
 *         操作，当这个操作执行后，流就被使用“光”了，无法再被操作。所以这必定是流的最后一个操作。Terminal
 *         操作的执行，才会真正开始流的遍历，并且会生成一个结果，或者一个 side effect。 在对于一个 Stream 进行多次转换操作
 *         (Intermediate 操作)，每次都对 Stream 的每个元素进行转换，而且是执行多次，这样时间复杂度就是 N（转换次数）个
 *         for 循环里把所有操作都做掉的总和吗？其实不是这样的，转换操作都是 lazy 的，多个转换操作只会在 Terminal
 *         操作的时候融合起来，一次循环完成。我们可以这样简单的理解，Stream 里有个操作函数的集合，每次转换操作就是把转换函数放入这个集合中，在
 *         Terminal 操作的时候循环 Stream 对应的集合，然后对每个元素执行所有的函数。
 */
public class StreamDemo {

    private static Stream<String> output(List<Integer> list){
        for(Integer i:list)
            System.out.println(System.nanoTime()+"++"+i);
        return Stream.of("ok");
    }
    public static void main(String[] args) {
        long start = System.nanoTime();
        Stream<List<Integer>> inputStream = Stream.of(Arrays.asList(1,11,111,1111),
                Arrays.asList(2, 3,22,33,222,333), Arrays.asList(4, 5, 6,7,8,9,10,100,1000,10000), Arrays.asList(44, 54, 64,74,84,94,104,1040,1000,10000));
        Stream<String> outputStream = inputStream
                .flatMap((childList) -> output(childList)).parallel();
        outputStream.collect(Collectors.toList());
        System.out.println("-------------flatMap end nano:"+(System.nanoTime()-start));
        
        // 4. 构造流的几种常见方法
        // 1. Individual values
        Stream stream = Stream.of("a", "b", "c");
        // 2. Arrays
        String[] strArray = new String[] { "a1", "b2", "c3" };
        stream = Stream.of(strArray);
        stream = Arrays.stream(strArray);
        // 3. Collections
        List<String> list = Arrays.asList(strArray);
        List<String> list2 = new ArrayList<String>();
        //stream = list.stream();
        list.parallelStream().forEach( s ->parseLine(s,list2));
        list2.stream().forEach(System.out::println);
        
        System.out.println("filter");
        demo1();
        System.out.println("sorted");
        sorted();
        System.out.println("map");
        map();
        System.out.println("distinct");
        distinct();
        System.out.println("reduce");
        reduce();
        System.out.println("collect");
        collect();
        System.out.println("minMax");
        minMax();
        System.out.println("upperCase");
        upperCase();
        System.out.println("square");
        square();
        System.out.println("flatMap");
        flatMap();
        System.out.println("testLimitAndSkip");
        testLimitAndSkip();

        java8streamapi();
    }

    public static void parseLine(String str,List list){
        list.add(str);
       // return str;
    }
    /**
     * https://www.ibm.com/developerworks/cn/java/j-lo-java8streamapi/
     */
    public static void java8streamapi() {
        // 清单 4. 构造流的几种常见方法
        // 1. Individual values
        Stream stream = Stream.of("a", "b", "c");
        // 2. Arrays
        String[] strArray = new String[] { "a", "b", "c" };
        stream = Stream.of(strArray);
        stream = Arrays.stream(strArray);
        // 3. Collections
        List<String> list = Arrays.asList(strArray);
        stream = list.stream();

        // 清单 5. 数值流的构造
        IntStream.of(new int[] { 1, 2, 3 }).forEach(System.out::println);
        IntStream.range(1, 3).forEach(System.out::println);
        IntStream.rangeClosed(1, 3).forEach(System.out::println);
        // 清单 6. 流转换为其它数据结构
        // 1. Array
        String[] strArray1 = (String[]) stream.toArray(String[]::new);
        // 2. Collection
        List<String> list1 = (List<String>) stream.collect(Collectors.toList());
        List<String> list2 = (List<String>) stream.collect(Collectors
                .toCollection(ArrayList::new));
        Set set1 = (Set) stream.collect(Collectors.toSet());
        Stack stack1 = (Stack) stream.collect(Collectors
                .toCollection(Stack::new));
        // 3. String
        String str = stream.collect(Collectors.joining()).toString();

        // 清单 7. 转换大写
        List<String> output = list.stream().map(String::toUpperCase)
                .collect(Collectors.toList());

        // 清单 8. 平方数
        List<Integer> nums = Arrays.asList(1, 2, 3, 4);
        List<Integer> squareNums = nums.stream().map(n -> n * n)
                .collect(Collectors.toList());

        // 清单 9. 一对多
        Stream<List<Integer>> inputStream = Stream.of(Arrays.asList(1),
                Arrays.asList(2, 3), Arrays.asList(4, 5, 6));
        Stream<Integer> outputStream = inputStream
                .flatMap((childList) -> childList.stream());

        // filter 对原始 Stream 进行某项测试，通过测试的元素被留下来生成一个新 Stream。
        // 清单 10. 留下偶数
        Integer[] sixNums = { 1, 2, 3, 4, 5, 6 };
        Integer[] evens = Stream.of(sixNums).filter(n -> n % 2 == 0)
                .toArray(Integer[]::new);
        // 经过条件“被 2 整除”的 filter，剩下的数字为 {2, 4, 6}。

        // 清单 13. peek 对每个元素执行操作并返回一个新的 Stream
        Stream.of("one", "two", "three", "four").filter(e -> e.length() > 3)
                .peek(e -> System.out.println("Filtered value: " + e))
                .map(String::toUpperCase)
                .peek(e -> System.out.println("Mapped value: " + e))
                .collect(Collectors.toList());

        // 清单 15. reduce 的用例
        // 字符串连接，concat = "ABCD"
        String concat = Stream.of("A", "B", "C", "D")
                .reduce("", String::concat);
        // 求最小值，minValue = -3.0
        double minValue = Stream.of(-1.5, 1.0, -3.0, -2.0).reduce(
                Double.MAX_VALUE, Double::min);
        // 求和，sumValue = 10, 有起始值
        int sumValue = Stream.of(1, 2, 3, 4).reduce(0, Integer::sum);
        // 求和，sumValue = 10, 无起始值
        sumValue = Stream.of(1, 2, 3, 4).reduce(Integer::sum).get();
        // 过滤，字符串连接，concat = "ace"
        concat = Stream.of("a", "B", "c", "D", "e", "F")
                .filter(x -> x.compareTo("Z") > 0).reduce("", String::concat);

        // 清单 22. 生成 10 个随机整数
        Random seed = new Random();
        Supplier<Integer> random = seed::nextInt;
        Stream.generate(random).limit(10).forEach(System.out::println);
        // Another way
        IntStream.generate(() -> (int) (System.nanoTime() % 100)).limit(10)
                .forEach(System.out::println);

        // 清单 23. 自实现 Supplier
        Stream.generate(new PersonSupplier())
                .limit(10)
                .forEach(
                        p -> System.out.println(p.getName() + ", " + p.getAge()));

        // Stream.iterate
        // iterate 跟 reduce 操作很像，接受一个种子值，和一个 UnaryOperator（例如 f）。然后种子值成为 Stream
        // 的第一个元素，f(seed) 为第二个，f(f(seed)) 第三个，以此类推。
        // 清单 24. 生成一个等差数列
        Stream.iterate(0, n -> n + 3).limit(10)
                .forEach(x -> System.out.print(x + " "));

        // groupingBy/partitioningBy
        // 清单 25. 按照年龄归组
        Map<Integer, List<Person>> personGroups = Stream
                .generate(new PersonSupplier()).limit(100)
                .collect(Collectors.groupingBy(Person::getAge));
        Iterator it = personGroups.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Integer, List<Person>> persons = (Map.Entry) it.next();
            System.out.println("Age " + persons.getKey() + " = "
                    + persons.getValue().size());
        }
        //清单 26. 按照未成年人和成年人归组
        Map<Boolean, List<Person>> children = Stream.generate(new PersonSupplier()).
         limit(100).
         collect(Collectors.partitioningBy(p -> p.getAge() < 18));
        System.out.println("Children number: " + children.get(true).size());
        System.out.println("Adult number: " + children.get(false).size());
    }

    /**
     * 3.1 filter() 对元素进行过滤 Demo（有一链表，{1,2,3,4,5}，把偶数过滤掉）：
     */
    public static void demo1() {
        List list = new ArrayList();
        for (int i = 1; i <= 5; ++i) {
            list.add(i);
        }
        list.stream().filter(param -> (int) param % 2 == 1)
                .forEach(System.out::println);
    }

    /**
     * 3.2 sorted() 对元素进行排序 Demo（有一链表，{2,3,1,5,4}，从小到大排序）：
     */
    public static void sorted() {
        List list = new ArrayList();
        list.add(2);
        list.add(3);
        list.add(1);
        list.add(5);
        list.add(4);
        list.stream().sorted().forEach(System.out::println);

        System.out.println("sorted desc");
        list.stream()
                .sorted((param1, param2) -> ((int) param1 < (int) param2 ? 1
                        : -1)).forEach(System.out::println);
    }

    /**
     * 3.3 map() 元素映射 也就是说，原来的链表的每个元素可以按照规则变成相应的元素。 Demo（链表 (1,0)，变成
     * true，false）：
     */
    public static void map() {
        List list = new ArrayList();
        list.add(1);
        list.add(0);
        list.stream().map(param -> (int) param == 1 ? true : false)
                .forEach(System.out::println);
    }

    /**
     * 3.4 distinct() 去除重复元素
     */
    public static void distinct() {
        List list = new ArrayList();
        list.add(1);
        list.add(1);
        list.add(0);
        list.stream().distinct().forEach(System.out::println);
    }

    /**
     * 3.5 reduce() ：把Stream 元素组合起来。 Demo（从1加到5）： 注意，reduce() 返回一个 Optional
     * 类型的对象，可以通过 get() 方法获得值。
     */
    public static void reduce() {
        List list = new ArrayList();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        System.out.println(list.stream()
                .reduce((param1, param2) -> (int) param1 + (int) param2).get());
    }

    /**
     * 3.6 collect() ：返回一个新的集合 Demo（先把 list 集合的 奇数去掉，然后把剩下的偶数返回到 _list 集合中）：
     */
    public static void collect() {
        List list = new ArrayList();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        List _list = (List) list.stream()
                .filter((param) -> (int) param % 2 == 0)
                .collect(Collectors.toList());
        _list.forEach(System.out::println);
    }

    /**
     * 3.7 min()，max() 找到最大值最小值 注意， min()，max() 方法也是返回 Optional 对象， 可以通过 get()
     * 方法返回值。
     */
    public static void minMax() {
        List list = new ArrayList();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);

        System.out.println(list.stream()
                .min((param1, param2) -> (int) param1 > (int) param2 ? 1 : -1)
                .get());
        System.out.println(list.stream()
                .max((param1, param2) -> (int) param1 > (int) param2 ? 1 : -1)
                .get());
    }

    public static void upperCase() {
        List<String> list = new ArrayList<String>();
        list.add("hello");
        list.add("stream");
        List<String> outpu = list.stream().map(String::toUpperCase)
                .collect(Collectors.toList());
        outpu.forEach(System.out::println);
    }

    /**
     * 计算平方
     */
    public static void square() {
        List<Integer> nums = Arrays.asList(1, 2, 3, 4);
        List<Integer> squareNums = nums.stream().map(n -> n * n)
                .collect(Collectors.toList());
        squareNums.forEach(System.out::println);
    }

    public static void flatMap() {
        Stream<List<Integer>> inputStream = Stream.of(Arrays.asList(1),
                Arrays.asList(2, 3), Arrays.asList(4, 5, 6));
        Stream<Integer> outputStream = inputStream
                .flatMap((childList) -> childList.stream());
        // inputStream.forEach(System.out::println);
        outputStream.forEach(System.out::println);
    }

    public static void testLimitAndSkip() {
        List<Person> persons = new ArrayList();
        for (int i = 1; i <= 10000; i++) {
            Person person = new Person(i, "name" + i);
            persons.add(person);
        }
        List<String> personList2 = persons.stream().map(Person::getName)
                .limit(10).skip(3).collect(Collectors.toList());
        System.out.println(personList2);
    }

}

class Person {
    public int no;
    private String name;
    private int age;

    public Person(int no, String name) {
        this.no = no;
        this.name = name;
    }

    public Person(int no, String name, int age) {
        this.no = no;
        this.name = name;
        this.age = age;
    }

    public String getName() {
        System.out.println(name);
        return name;
    }

    public int getAge() {
        return age;
    }
}

class PersonSupplier implements Supplier<Person> {
    private int index = 0;
    private Random random = new Random();

    @Override
    public Person get() {
        return new Person(index++, "StormTestUser" + index, random.nextInt(100));
    }
}
