package ${fragmentPackageName};

import android.os.Bundle;

import ${packageName}.app.base.BaseFragment;


import ${componentPackageName}.Dagger${pageName}Component;
import ${moudlePackageName}.${pageName}Module;
import ${contractPackageName}.${pageName}Contract;
import ${presenterPackageName}.${pageName}Presenter;

import ${packageName}.R;



public class ${pageName}Fragment extends BaseFragment<${pageName}Presenter> implements ${pageName}Contract.View{


    public static ${pageName}Fragment newInstance() {
        ${pageName}Fragment fragment = new ${pageName}Fragment();
        return fragment;
    }

    @Override
    public void initComponent() {
        Dagger${pageName}Component //如找不到该类,请编译一下项目
                .builder()
                .appComponent(getAppComponent())
                .${extractLetters(pageName[0]?lower_case)}${pageName?substring(1,pageName?length)}Module(new ${pageName}Module(this))
                .build()
                .inject(this);
    }

    @Override
    protected int getContentLayoutId()
    {
        return R.layout.${fragmentLayoutName};
    }


    @Override
    public void initData(Bundle savedInstanceState) {
        //setState(LoadingPage.STATE_SUCCESS);//如果不需要网络请求的话可以去掉注释 直接设置成功状态

    }

    /**
     * 此方法是让外部调用使fragment做一些操作的,比如说外部的activity想让fragment对象执行一些方法,
     * 建议在有多个需要让外界调用的方法时,统一传Message,通过what字段,来区分不同的方法,在setData
     * 方法中就可以switch做不同的操作,这样就可以用统一的入口方法做不同的事
     *
     * 使用此方法时请注意调用时fragment的生命周期,如果调用此setData方法时onCreate还没执行
     * setData里却调用了presenter的方法时,是会报空的,因为dagger注入是在onCreated方法中执行的,然后才创建的presenter
     * 如果要做一些初始化操作,可以不必让外部调setData,在initData中初始化就可以了
     *
     * @param data
     */

    public void setData(Object data) {

    }

    @Override
    protected void retryRequestData(){

    }


    @Override
    public void hidePullLoading() {

    }

    @Override
    public void showMessage(String message) {
        showSnackbar(message);
    }





}
