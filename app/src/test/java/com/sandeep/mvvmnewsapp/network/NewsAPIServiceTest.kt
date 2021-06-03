package com.sandeep.mvvmnewsapp.network

import com.google.common.truth.Truth.assertThat
import com.sandeep.mvvmnewsapp.utils.Constants
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by SandeepY on 31052021
 **/


class NewsAPIServiceTest {

    private lateinit var service: NewsAPIService
    private lateinit var server: MockWebServer

    @Before
    fun setUp() {
        server = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(server.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsAPIService::class.java)
    }

    private fun enqueueMockResponse(
        fileName: String
    ){
        val inputStream = javaClass.classLoader!!.getResourceAsStream(fileName)
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        mockResponse.setBody(source.readString(Charsets.UTF_8))
        server.enqueue(mockResponse)
    }

    @Test
    fun getTopHeadlines_sentRequest_receivedExpected(){
        runBlocking {
            enqueueMockResponse("newsresponse.json")
            val responseBody = service.getTopHeadlines(applyQueries()).body()
            val request = server.takeRequest()
            assertThat(responseBody).isNotNull()
            assertThat(request.path).isEqualTo("/v2/top-headlines?country=in&apiKey=&page=1")
        }
    }

    @Test
    fun getTopHeadlines_receivedResponse_correctPageSize(){
        runBlocking {
            enqueueMockResponse("newsresponse.json")
            val responseBody = service.getTopHeadlines(applyQueries()).body()
            val articleList = responseBody!!.articles
            assertThat(articleList.size).isEqualTo(20)
        }
    }

    @Test
    fun getTopHeadlines_receivedResponse_correctContent(){
        runBlocking {
            enqueueMockResponse("newsresponse.json")
            val responseBody = service.getTopHeadlines(applyQueries()).body()
            val articleList = responseBody!!.articles
            val article = articleList[1]
            assertThat(article.author).isEqualTo("Mashable News Staff")
            assertThat(article.url).isEqualTo("https://in.mashable.com/science/20780/see-pic-nasas-perseverance-rover-sends-images-from-its-first-drive-on-the-red-planet")
            assertThat(article.publishedAt).isEqualTo("2021-03-09T10:35:22Z")
        }
    }



    fun applyQueries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()
        queries[Constants.QUERY_COUNTRY] = Constants.QUERY_COUNTRY_VALUE
        queries[Constants.QUERY_PAGE] = Constants.QUERY_PAGE_VALUE
        queries[Constants.QUERY_API_KEY] = Constants.QUERY_API_KEY_VALUE
        return queries
    }

    @After
    fun tearDown() {
        server.shutdown()
    }
}