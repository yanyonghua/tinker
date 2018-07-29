package www.yyh.com.tinkertest;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.tencent.tinker.loader.app.ApplicationLike;
import com.tinkerpatch.sdk.TinkerPatch;
import com.tinkerpatch.sdk.loader.TinkerPatchApplicationLike;

/**
 * Created by 56357 on 2018/7/29
 */
public class TestApplication extends Application {

    private ApplicationLike tinkerApplicationLike;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //
        MultiDex.install(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // 我们可以从这里获得Tinker加载过程的信息
        tinkerApplicationLike = TinkerPatchApplicationLike.getTinkerPatchApplicationLike();

        // 初始化TinkerPatch SDK, 更多配置可参照API章节中的,初始化SDK
        TinkerPatch.init(tinkerApplicationLike)
                .reflectPatchLibrary()
                .setPatchRollbackOnScreenOff(true)
                .setPatchRestartOnSrceenOff(true)
                .setFetchPatchIntervalByHours(3);

        // 每隔3个小时(通过setFetchPatchIntervalByHours设置)去访问后台时候有更新,通过handler实现轮训的效果
        TinkerPatch.with().fetchPatchUpdateAndPollWithInterval();
    }
}
