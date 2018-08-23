package org.langke.java5;

import java.util.*;
public class Generic {

	/**
	 * 在这个简单的例子中，我们在定义aList的时候指明了它是一个直接受Integer类型的ArrayList，当我们调用aList.get(0)时，我们已经不再需要先显式的将结果转换成Integer，然后再赋值给myInteger了。
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<Integer> aList = new ArrayList<Integer>();
		aList.add(new Integer(1));
		Integer myInteger = aList.get(0);
		Map map = new HashMap();
		
		 

	}
}


/**
 * 单就这个例子而言，泛型至少还有一个更大的好处，那就是使用了泛型的容器类变得更加健壮：早先，Collection接口的get()和Iterator接口的next()方法都只能返回Object类型的结果，我们可以把这个结果强制转换成任何Object的子类，而不会有任何编译期的错误，但这显然很可能带来严重的运行期错误，因为在代码中确定从某个Collection中取出的是什么类型的对象完全是调用者自己说了算，而调用者也许并不清楚放进Collection的对象具体是什么类的；就算知道放进去的对象“应该”是什么类，也不能保证放到Collection的对象就一定是那个类的实例。现在有了泛型，只要我们定义的时候指明该Collection接受哪种类型的对象，编译器可以帮我们避免类似的问题溜到产品中。我们在实际工作中其实已经看到了太多的ClassCastException，不是吗？
 */
  class TestGenerics<Generic> {

    Collection<Generic> col;

    public void doSth(Generic elem) {

        col.add(elem);

        // ...

    }

    public static <T extends TestGenerics> void add (Collection<T> c, T elem) {

      c.add(elem);

  }
    /**
     * 个人认为泛型是这次J2SE(TM) 5.0中引入的最重要的语言元素，给Java语言带来的影响也是最大。举个例子来讲，我们可以看到，几乎所有的Collections API都被更新成支持泛型的版本。这样做带来的好处是显而易见的，那就是减少代码重复（不需要提供多个版本的某一个类或者接口以支持不同类的对象）以及增强代码的健壮性（编译期的类型安全检查）。不过如何才能真正利用好这个特性，尤其是如何实现自己的泛型接口或类供他人使用，就并非那么显而易见了。让我们一起在使用中慢慢积累。
     */
}


