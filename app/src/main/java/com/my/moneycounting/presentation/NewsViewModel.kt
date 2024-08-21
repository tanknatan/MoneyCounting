package com.my.moneycounting.presentation


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.my.moneycounting.data.NewsArticle
import com.my.moneycounting.domain.FinnhubApi

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

class NewsViewModel : ViewModel() {
    private val _newsArticles = MutableStateFlow<List<NewsArticle>>(emptyList())
    val newsArticles: StateFlow<List<NewsArticle>> = _newsArticles

    init {
        fetchMarketNews()
    }

    private fun fetchMarketNews() {
        viewModelScope.launch {
            try {
                val response = FinnhubApi.RetrofitInstance.api.getMarketNews(apiKey = "cr2th6hr01qgsq6mvab0cr2th6hr01qgsq6mvabg")
                _newsArticles.value = response
            } catch (e: HttpException) {
                // Обработка HTTP-ошибок
                when (e.code()) {
                    401 -> {
                        // Неавторизован, возможно, API-ключ неверен
                        println("HTTP 401 Неавторизован - Проверьте API-ключ")
                    }
                    403 -> {
                        // Запрещено, возможно, у API-ключа нет нужных разрешений
                        println("HTTP 403 Запрещено - Проверьте разрешения API-ключа")
                    }
                    404 -> {
                        // Не найдено, возможно, неверный URL-адрес конечной точки
                        println("HTTP 404 Не найдено - Проверьте URL-адрес конечной точки")
                    }
                    else -> {
                        // Другие HTTP-ошибки
                        println("HTTP ${e.code()} Ошибка: ${e.message()}")
                    }
                }
            } catch (e: Exception) {
                // Обработка других типов исключений
                println("Произошла ошибка: ${e.message}")
            }
        }
    }

}


