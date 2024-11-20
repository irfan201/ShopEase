package com.example.shopease.utils

import com.example.shopease.data.model.OrderDto
import com.example.shopease.data.model.OrderHistoryDetailDto
import com.example.shopease.data.model.OrderHistoryDto
import com.example.shopease.data.model.ProductCartEntity
import com.example.shopease.data.model.ProductCategoryDto
import com.example.shopease.data.model.ProductDetailDto
import com.example.shopease.data.model.ProductDto
import com.example.shopease.data.model.ProductFavoriteEntity
import com.example.shopease.domain.model.Order
import com.example.shopease.domain.model.OrderHistory
import com.example.shopease.domain.model.Product
import com.example.shopease.domain.model.User
import com.google.firebase.auth.FirebaseUser

object DataMapper {
    fun FirebaseUser.toUser(): User {
        return User(
            uid = uid, name = displayName ?: "", email = email ?: "", photoUrl = photoUrl.toString()
        )
    }

    fun ProductDto.Data.toProduct(): Product {
        return Product(
            id = pdId,
            title = pdName,
            price = pdPrice,
            description = pdDescription,
            category = categories.joinToString { it.ctName },
            image = listOf(pdImageUrl, pdData.imageUrl2, pdData.imageUrl3),
            rating = rating
        )
    }

    fun ProductDetailDto.Data.toProduct(): Product {
        return Product(
            id = pdId,
            title = pdName,
            price = pdPrice,
            description = pdDescription,
            category = categories.joinToString { it.ctName },
            image = listOf(pdImageUrl, pdData.imageUrl2, pdData.imageUrl3),
            rating = reviews.first().rvRating,
        )
    }

    fun ProductCategoryDto.Data.Product.toProduct(category: String): Product {
        return Product(
            id = pdId,
            title = pdName,
            price = pdPrice,
            description = pdDescription,
            category = category,
            image = listOf(pdImageUrl, pdData.imageUrl2, pdData.imageUrl3),
            rating = rating
        )

    }

    fun ProductFavoriteEntity.toProduct(): Product {
        return Product(
            id = productId,
            title = productName,
            price = productPrice,
            description = productDescription,
            category = productCategory,
            image = listOf(productImage),
            rating = productRating,
            userId = userId,
            isFavorite = isFavorite
        )

    }

    fun ProductCartEntity.toProduct(): Product {
        return Product(
            id = productId,
            title = productName,
            price = productPrice,
            description = productDescription,
            category = productCategory,
            image = listOf(productImage),
            rating = productRating,
            userId = userId
        )
    }

    fun OrderDto.Data.toOrder(): Order {
        return Order(
            orId = orId,
            orUsId = orUsId,
            totalPrice = orTotalPrice,
            orToken = transaction.token,
            orUrl = transaction.redirectUrl
        )
    }

    fun OrderHistoryDto.Data.toOrderHistory(): OrderHistory {
        return OrderHistory(
            orId = orPlatformId, totalPrice = orTotalPrice, orderDetail = details.map { details ->
                OrderHistory.OrderDetail(
                    id = details.odProducts.first().id,
                    name = details.odProducts.first().name,
                    price = details.odProducts.first().price,
                    quantity = details.odProducts.first().quantity,
                    imageUrl = details.odProducts.first().imageUrl.pdImageUrl
                )
            }, statusPayment = orStatus, dateOrder = orCreatedOn
        )
    }

    fun OrderHistoryDetailDto.Data.Detail.OdProduct.toOrderDetail(): OrderHistory.OrderDetail {
        return OrderHistory.OrderDetail(
            id = id, name = name, price = price, quantity = quantity, imageUrl = imageUrl.pdImageUrl
        )
    }


}