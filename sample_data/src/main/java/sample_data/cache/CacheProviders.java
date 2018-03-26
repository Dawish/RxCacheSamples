package sample_data.cache;

import io.reactivex.Observable;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictDynamicKey;
import io.rx_cache2.EvictProvider;
import io.rx_cache2.Expirable;
import io.rx_cache2.LifeCache;
import io.rx_cache2.Reply;
import java.util.List;
import java.util.concurrent.TimeUnit;
import sample_data.entities.Repo;
import sample_data.entities.User;

/**
 * Created by victor on 04/01/16.
 *
 * 详情请看: http://geek.csdn.net/news/detail/134086
 *
 */
public interface CacheProviders {
    /***
     * danxx:
     * 大概的原理就是传入一个 rxjava 和 retrofit组成的网络Observable 通过RxCache内部的操作 完成网络数据缓存刷新等
     *
     * @param oRepos   此Observable的意义为需要将你想缓存的Retrofit接口作为参数传入(返回值必须为Observable),
     *                  RxCache会在没有缓存,或者缓存已经过期,或者EvictProvider为true时,通过这个Retrofit接口
     *                  重新请求最新的数据,并且将服务器返回的结果包装成Reply返回,返回之前会向内存缓存和磁盘
     *                  缓存中各保存一份
     *
     * @param userName DynamicKey 缓存的key，比如分页的时候需要传入页数
     * @param evictDynamicKey EvictDynamicKey 如果为true,就会重新通过Retrofit获取新的数据,
     *                                         如果为false就会使用这个缓存
     * @return
     */
    @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
    Observable<Reply<List<Repo>>> getRepos(Observable<List<Repo>> oRepos, DynamicKey userName, EvictDynamicKey evictDynamicKey);


    /**返回数据需要被Reply包装是因为RxCache返回的数据加密了时会被Reply包装了，如果没有加密则不需要包装*/
    @Expirable(false)  //可以保证此接口的数据在缓存快满了的时候不会被回收
    @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
    Observable<Reply<List<User>>> getUsers(Observable<List<User>> oUsers, DynamicKey idLastUserQueried, EvictProvider evictProvider);


    Observable<Reply<User>> getCurrentUser(Observable<User> oUser, EvictProvider evictProvider);
}
