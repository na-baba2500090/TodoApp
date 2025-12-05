package app.todo;

public class Todo {
	private String title; //ToDoのタイトル
	private boolean done; //完了済みかどうか
	private int priority; //優先度
	
	//コンストラクタ
	public Todo(String title, boolean done) {
		this.title = title;
		this.done = done;
	}
	public Todo(String title) {
		this(title, false);
	}
	
	//getter
	public String getTitle() {
		return this.title;
	}
	public boolean getDone() {
		return this.done;
	}
	public int getPriority() {
		return this.priority;
	}
	
	//setter
	public void setTitle(String title) {
		this.title = title;
	}
	public void setDone(boolean done) {
		this.done = done;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	
	@Override
	//toStringメソッド。println()の中にTodoクラスの変数を入れるだけで情報を表示してくれる
	public String toString() {
		//三項演算子（条件 ? 真のときの値 : 偽のときの値）
		//を使い、done=trueのときは[済]を、falseのときは[未]をタイトルの前につける
		String priorityName = null;
		switch (this.priority) {
		case 1 -> {
			priorityName = "高";
		}
		case 2 -> {
			priorityName = "中";
		}
		case 3 -> {
			priorityName = "低";
		}
		}
		return (this.done ? "[済]" : "[未]") + " " 
			+ this.title + "（優先度：" + priorityName + "）";
	}
}
