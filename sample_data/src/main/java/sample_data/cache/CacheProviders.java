package sample_data.cache;

import io.reactivex.Observable;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictDynamicKey;
import io.rx_cache2.EvictProvider;
import io.rx_cache2.LifeCache;
import io.rx_cache2.Reply;
import java.util.List;
import java.util.concurrent.TimeUnit;
import sample_data.entities.Repo;
import sample_data.entities.User;

/**
 * Created by victor on 04/01/16.
 */
public interface CacheProviders {
    /**
     *  使用Reply包装的原因:
     */

    /***
     * danxx
     * 大概的原理就是传入一个 rxjava 和 retrofit组成的网络Observable 通过RxCache内部的操作 完成网络数据缓存刷新等
     * @param oRepos rxjava 和 retrofit组成的网络Observable
     * @param userName DynamicKey
     * @param evictDynamicKey EvictDynamicKey
     * @return
     */
    @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
    Observable<Reply<List<Repo>>> getRepos(Observable<List<Repo>> oRepos, DynamicKey userName, EvictDynamicKey evictDynamicKey);

    @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
    Observable<Reply<List<User>>> getUsers(Observable<List<User>> oUsers, DynamicKey idLastUserQueried, EvictProvider evictProvider);

    Observable<Reply<User>> getCurrentUser(Observable<User> oUser, EvictProvider evictProvider);
}
