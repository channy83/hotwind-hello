import javax.cache.Cache
import javax.cache.CacheManager
import javax.cache.Caching
import javax.cache.MutableConfiguration
import javax.cache.spi.CachingProvider


def configCache(cacheName) {
	def cp = Caching.getCachingProvider()
	def cm = cp.getCacheManager()	
	def cacheConfig = new MutableConfiguration()
	//
	cm.configureCache(cacheName, cacheConfig)
}

// 
configCache("hello")
configCache("JCacheRequestCheckKvStore")