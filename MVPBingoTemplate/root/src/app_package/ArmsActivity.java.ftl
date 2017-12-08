package ${ativityPackageName};

import android.os.Bundle;


import ${packageName}.app.base.BaseTitleActivity;

import ${componentPackageName}.Dagger${pageName}Component;
import ${moudlePackageName}.${pageName}Module;
import ${contractPackageName}.${pageName}Contract;
import ${presenterPackageName}.${pageName}Presenter;

import ${packageName}.R;



public class ${pageName}Activity extends BaseTitleActivity<${pageName}Presenter> implements ${pageName}Contract.View {


    @Override
    public void setupActivityComponent() {
        Dagger${pageName}Component //如找不到该类,请编译一下项目
                .builder()
                .appComponent(getAppComponent())
                .${extractLetters(pageName[0]?lower_case)}${pageName?substring(1,pageName?length)}Module(new ${pageName}Module(this))
                .build()
                .inject(this);
    }

    @Override
    public int getContentLayoutId() {
        return R.layout.${activityLayoutName}; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void loadData(Bundle savedInstanceState) {
        super.loadData(savedInstanceState);
        //setState(LoadingPage.STATE_SUCCESS);//如果不需要网络请求的话可以去掉注释 直接设置成功状态
        init();//可以在此进行初始化
    }

    private void init(){

    }

    @Override
    protected void retryRequestData(){

    }


    @Override
    public void hidePullLoading(){

    }

    @Override
    public void showMessage(String message) {
        showSnackbar(message);
    }




}
