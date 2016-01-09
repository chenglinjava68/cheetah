package cheetah.domain;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Max on 2016/1/9.
 */
public abstract class RepositoryManager {
    protected final Map<String, Repository> repositoryMap = new ConcurrentHashMap<String, Repository>();
    protected final Map<String, PagingRepository> pagingRepositoryMap = new ConcurrentHashMap<String, PagingRepository>();


}
