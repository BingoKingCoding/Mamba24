package ${contractPackageName};



public interface ${pageName}Contract {
    //对于经常使用的关于UI的方法可以定义到IBaseView中,如获取数据成功,和显示文字消息等等
    interface View extends IBaseView {

    }
    //Presenter层定义接口,外部只需关心Presenter返回的数据,无需关心内部细节
    interface Presenter extends IBasePresenter{

    }
}
