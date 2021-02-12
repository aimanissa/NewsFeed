package com.aimanissa.android.newsfeed.ui.fragments.feed.interactor

import com.aimanissa.android.newsfeed.data.app.api.NewsEndpoint
import com.aimanissa.android.newsfeed.data.app.db.repository.NewsRepositoryImpl
import com.aimanissa.android.newsfeed.data.app.mapper.ResponseMapper
import com.aimanissa.android.newsfeed.data.app.model.NewsItem
import com.aimanissa.android.newsfeed.di.modules.ApiModule.Companion.API_KEY
import io.reactivex.Single
import javax.inject.Inject

class NewsFeedLoader @Inject constructor(
    private var endPoint: NewsEndpoint,
    private var repository: NewsRepositoryImpl,
    private var mapper: ResponseMapper
) {

    fun loadNews(): Single<List<NewsItem>> {
        return endPoint.loadNews(QUERY_COUNTRY, QUERY_SIZE, API_KEY)
            .map { it.articles }
            .map { mapper.newsApiListToNewsItemsList(it) }
            .doOnSuccess {
                repository.apply {
                    deleteAll()
                    saveAll(it)
                }
            }
            .flatMap { loadNewsFromDb() }
    }

    fun loadNewsFromDb(): Single<List<NewsItem>> {
        return Single.fromCallable { repository.getAll() }
    }

    fun loadSearchNews(query: String): Single<List<NewsItem>> {
        return endPoint.searchNews(query, API_KEY)
            .map { it.articles }
            .map { mapper.newsApiListToNewsItemsList(it) }
            .doOnSuccess {
                repository.apply {
                    deleteAll()
                    saveAll(it)
                }
            }
            .flatMap { loadNewsFromDb() }
    }

    companion object {
        private const val QUERY_COUNTRY = "ru"
        private const val QUERY_SIZE = "30"
    }
}

