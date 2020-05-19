package test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class Cal24 {

	public static void main(String[] args) {
		Cal24 ca = new Cal24();
		String[] st = new String[] { "2", "11", "12", "9" };
		String[] ans = new String[] {"12"};

		ca.cal(0, st, new String[st.length * 2 - 1]);
		StringBuffer sb = new StringBuffer();

		ca.cal2(st, ans);
		Map<String, Set<String>> result = ca.getResult();
		int step = 1;
		List<String> results = new ArrayList<String>();  
		for (Map.Entry<String, Set<String>> en : result.entrySet()) {
			Set<String> set = en.getValue();
			sb.append(" 【" + en.getKey() + "】共 " + set.size() + " 条\n");
			Iterator<String> it = set.iterator();
			
			while (it.hasNext()) {
				String str = (String) it.next();
				sb.append(""+step+": "+str +"\n");
				step++;
				if((step % 10)==0) {
					results.add(sb.toString());
					sb =  new StringBuffer(); 
				}
				
			}
			
		}
		if(sb.length()!=0) {
			results.add(sb.toString());
			sb =  new StringBuffer();
		}
		if(results.size()>2) {
			new Thread() {
				public void run() {
					for(int i=1;i<results.size();i++) {
						try {
							Thread.sleep(1);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						System.out.print(results.get(i));	
						System.out.println("2=====");
					}
				}
			}.start();	
		}
		System.out.print(results.get(0));
		System.out.println("1=====");
		
		System.out.println(ca.total);
		System.out.println(sb.toString()+"===");

	}
	// * + 11 - 10 9 2

	private Map<String, Integer> total = new HashMap<String, Integer>();

	public boolean distinct(String str, String fc) {
		for (int i = 0; i < fu.length; i++) {
			if (!fc.equals(fu[i]) && str.indexOf(fu[i]) != -1) {
				return false;
			}
		}
		return true;
	}

	public void cal2(String[] shu, String[] ans) {
		Iterator<String> it = m.iterator();
		while (it.hasNext()) {
			String[] attr = it.next().split(",");

			setAttr(attr, shu, ans);
		}
	}

	String[] fu = new String[] { "+", "-", "*", "/" };

	public void setAttr(String[] attr, String[] shu, String[] ans) {
		List<Integer> mark = new ArrayList<Integer>();
		for (int k = 0; k < attr.length; k++) {
			String item = attr[k];
			if (isN(item)) {
				int i = Integer.parseInt(item) - 1;
				attr[k] = shu[i];
			} else {
				mark.add(k);
			}
		}
		stack = new Stack<String>();

		f(fu.length - 1, 0, mark, attr, ans);

	}

	public Stack<String> stack = new Stack<String>();

	private void f(int targ, int has, List<Integer> mark, String[] attr, String[] ans) {
		if (has == targ) {
			Iterator<String> it = stack.iterator();
			int i = 0;
			while (it.hasNext()) {
				String str = (String) it.next();
				attr[mark.get(i)] = str;
				i++;
			}
			add("res");
			jisuan2(attr, ans);
			return;
		}

		for (int i = 0; i < fu.length; i++) {
			// if (!stack.contains(fu[i])) {
			stack.add(fu[i]);
			f(targ, has + 1, mark, attr, ans);
			stack.pop();
			// }
		}

	}

	private boolean isN(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}

	public void cal(int idx, String[] shu0, String[] res) {
		String[] shu = new String[shu0.length];
		for (int i = 0; i < shu.length; i++) {
			shu[i] = (i + 1) + "";
		}

		String[] fu = new String[] { "+" };
		if (idx == 0) {
			for (int i = 0; i < shu.length; i++) {
				res[idx] = shu[i];
				cal(idx + 1, shu0, res);
			}

		} else if (idx == res.length - 1) {
			List<String> lshu = shu(res, shu);
			if (lshu.size() == 0) {
				Map<String, String> shu1 = new HashMap<String, String>();
				for (int i = 0; i < shu.length; i++) {

					shu1.put(shu[i], shu0[i]);
				}
				for (int i = 0; i < fu.length; i++) {
					res[idx] = fu[i];
					String[] attr = new String[res.length];
					for (int j = 0; j < attr.length; j++) {
						String v = res[attr.length - j - 1];
						attr[j] = v;
					}

					jisuan(attr, shu);
				}
			}
		} else {
			List<String> lshu = shu(res, shu);

			for (int i = 0; i < fu.length; i++) {
				lshu.add(fu[i]);
			}
			for (int i = 0; i < lshu.size(); i++) {
				res[idx] = lshu.get(i);
				cal(idx + 1, shu0, res);
			}

		}

	}

	private void jisuan2(String[] attr, String[] ans) {

		Stack<String> stack = new Stack<String>(); // 定义栈
		Stack<Object> stack2 = new Stack<Object>(); //
		Stack<String> stack3 = new Stack<String>(); // 符号

		List<String> fh = new ArrayList<String>();
		boolean temp = true; // 定义一个临时变量
		int chars = 0; // 用于记录符号数
		int num = 0; // 用于记录数字的个数
		for (int i = attr.length - 1; i >= 0; i--) {
			try { // 从右向左遍历，遇到数字压栈
				if (!(attr[i].equals("-") || attr[i].equals("+") || attr[i].equals("/") || attr[i].equals("*"))) {
					stack.push(attr[i]); // 转换为double类型的
					stack2.push(attr[i]);

					num++; // 记录数字个数
				} else { // 遇到符号把栈顶的前两个数字出栈
					fh.add(attr[i]);
					String a = stack.pop();// 出栈
					String b = stack.pop();// 出栈
					chars++;// 字符的个数
					String result = SymCal(a, b, attr[i]); // 调用函数计算结果值
					String pre = null;
					if (!stack3.isEmpty()) {
						pre = stack3.pop();
					}

					stack3.push(attr[i]);
					stack.push(result); // 结果进栈

					Object a2 = stack2.pop();// 出栈
					Object b2 = stack2.pop();// 出栈
					if ("*".equals(attr[i]) || "+".equals(attr[i])) {
						double ax = Double.parseDouble(a);
						double bx = Double.parseDouble(b);
						if (ax > bx) {
							stack2.push("( " + a2 + " " + attr[i] + " " + b2 + " )"); // 结果进栈
						} else {
							stack2.push("( " + b2 + " " + attr[i] + " " + a2 + " )"); // 结果进栈
						}
					} else {
						stack2.push("( " + a2 + " " + attr[i] + " " + b2 + " )"); // 结果进栈
					}

				}

			} catch (Exception e) {
				temp = true; // 抛出的异常

			}
			temp = false;

		}

		if (temp || !(num - 1 == chars) || stack.empty()) { // 没有进入for循环 或者栈为空，或者符号不够
		} else {
			double ans0 = Double.parseDouble(stack.pop()); // 让最后一个结果出栈

			Set<Double> ans1 = new HashSet<Double>();
			for (int i = 0; i < ans.length; i++) {
				ans1.add(Double.parseDouble(ans[i]));
			}

			if (ans1.contains(ans0)) {

				String s = stack2.pop() + "";
				String key = (int) ans0 + "";
				s = s.substring(1, s.length() - 1) + " = " + key;
				s = s.replaceAll("  ", " ");
				if (!result.containsKey(key)) {
					result.put(key, new HashSet<String>());
				}
				Set<String> set = result.get(key);
				set.add(s);

				result.put(key, set);
			}

		}
	}

	private void jisuan(String[] attr, String[] shu) {

		Stack<String> stack = new Stack<String>(); // 定义栈

		List<String> fh = new ArrayList<String>();
		boolean temp = true; // 定义一个临时变量
		int chars = 0; // 用于记录符号数
		int num = 0; // 用于记录数字的个数
		for (int i = attr.length - 1; i >= 0; i--) {
			try { // 从右向左遍历，遇到数字压栈
				if (!(attr[i].equals("-") || attr[i].equals("+") || attr[i].equals("/") || attr[i].equals("*"))) {
					stack.push(attr[i]); // 转换为double类型的
					num++; // 记录数字个数
				} else { // 遇到符号把栈顶的前两个数字出栈
					fh.add(attr[i]);
					String a = stack.pop();// 出栈
					String b = stack.pop();// 出栈
					chars++;// 字符的个数
					String result = SymCal(a, b, attr[i]); // 调用函数计算结果值

					stack.push(result); // 结果进栈

				}

			} catch (Exception e) {
				temp = true; // 抛出的异常

			}
			temp = false;

		}
		if (temp || !(num - 1 == chars) || stack.empty()) { // 没有进入for循环 或者栈为空，或者符号不够

			// System.out.println(StringUtils.join(attr, " "));
		} else {
			sy(attr, shu);

			// System.out.println(StringUtils.join(attr, " "));
		}
	}

	private void add(String k) {
		if (!total.containsKey(k)) {
			total.put(k, 0);
		} else {
			int v = total.get(k);
			total.put(k, v + 1);
		}
	}

	private void sy(String[] attr, String[] shu) {
		String tmp = StringUtils.join(attr, ",");
		m.add(tmp);
	}

	private Set<String> m = new HashSet<String>();
	private Map<String, Set<String>> result = new HashMap<String, Set<String>>();

	public Map<String, Set<String>> getResult() {
		return result;
	}

	public Set<String> getM() {
		return m;
	}

	private String SymCal(String a, String b, String s) { // 用于计算
		BigDecimal b1 = new BigDecimal(a);
		BigDecimal b2 = new BigDecimal(b);
		switch (s) {
		case "*":
			return b1.multiply(b2).doubleValue() + "";
		// return a * b;
		case "/":
			return b1.divide(b2, 20, BigDecimal.ROUND_HALF_UP).doubleValue() + "";
		// return a / b;
		case "+":
			return b1.add(b2).doubleValue() + "";
		// return a + b;
		case "-":
			return b1.subtract(b2).doubleValue() + "";
		// return a - b;
		}
		return "0";
	}

	private List<String> shu(String[] res, String[] shu) {
		List<String> lshu = new ArrayList<String>();
		for (int i = 0; i < shu.length; i++) {
			lshu.add(shu[i]);
		}
		Set<String> lres = new HashSet<String>();
		for (int i = 0; i < res.length; i++) {
			lres.add(res[i]);
		}

		lshu.removeAll(lres);

		return lshu;
	}

}
