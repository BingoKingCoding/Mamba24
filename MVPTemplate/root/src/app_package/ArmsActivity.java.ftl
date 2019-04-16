package ${ativityPackageName};


import ${applicationPackage}.app.base.BaseActivity;
import ${applicationPackage}.R;


public class ${pageName}Activity extends BaseActivity<${pageName}Presenter> implements ${pageName}Contract.View {


    @Override
    public void initActivityComponent() {
        Dagger${pageName}Component //如找不到该类,请编译一下项目
                .builder()
                .appComponent(getAppComponent())
                .${extractLetters(pageName[0]?lower_case)}${pageName?substring(1,pageName?length)}Module(new ${pageName}Module(this))
                .build()
                .inject(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.${activityLayoutName}; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(){

    }


}
