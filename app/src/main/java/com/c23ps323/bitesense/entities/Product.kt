package com.c23ps323.bitesense.entities

data class Product(
    var id: String = "",
    var name: String = "",
    var photoUrl: String = "",
    var category: String = "",
    var description: String = "",
    var warningIndicator: Int = 0,
    var isFavorite: Boolean = false,
)

object ProductData {
    private var data = arrayOf(
        arrayOf(
            "P-001",
            "Coca Cola",
            "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.suara.com%2Flifestyle%2F2021%2F06%2F17%2F133251%2Fmanfaat-coca-cola-pembersih-kloset-hingga-pengendali-hama&psig=AOvVaw1KvfydFb6yBI2r7wNBufL1&ust=1684563185952000&source=images&cd=vfe&ved=0CBEQjRxqFwoTCJivxITdgP8CFQAAAAAdAAAAABAE",
            "Coke",
            "Coca-Cola, or Coke, is a carbonated soft drink manufactured by the Coca-Cola Company. In 2013, Coke products were sold in over 200 countries worldwide, with consumers drinking more than 1.8 billion company beverage servings each day.",
            0,
            false,
        ),
        arrayOf(
            "P-002",
            "Aqua",
            "https://www.google.com/imgres?imgurl=https%3A%2F%2Fimages.k24klik.com%2Fproduct%2Fapotek_online_k24klik_20211206100212359225_AQUA-AIR-MINERAL-1500ML.jpg&tbnid=CbWBr7fFliyo8M&vet=12ahUKEwjiydPe3YD_AhU2ktgFHdVLBbYQMygbegUIARCiAg..i&imgrefurl=https%3A%2F%2Fwww.k24klik.com%2Fp%2Faqua-air-mineral-1500ml-28864&docid=MayICv1McG81hM&w=300&h=300&q=aqua&ved=2ahUKEwjiydPe3YD_AhU2ktgFHdVLBbYQMygbegUIARCiAg",
            "Mineral Water",
            "Air mineral AQUA berasal dari sumber mata air yang terpilih, serta memiliki Tiga Perlindungan, yaitu; melindungi ekosistem sumber airnya, menjaga kealamian mineralnya, serta diproses secara saksama untuk menjaga keasliannya sampai ke tangan anda.",
            1,
            true,
        ),
        arrayOf(
            "P-003",
            "NU Milk Tea",
            "https://img.priceza.co.id/img2/2130004/0001/2130004-20211117042123-817728574273306.jpg",
            "Milk Tea",
            "Nu Milk Tea merupakan minuman teh susu dalam kemasan pertama di Indonesia. Terbuat dari kombinasi tepat antara ekstrak daun teh premium dengan kekentalan susu yang nikmat, memberikan sensasi menyenangkan dan membantumu rileks menghadapi hari. Tanpa pengawet, pewarna, dan pemanis buatan sehingga aman untuk dikonsumsi.",
            2,
            true,
        ),
        arrayOf(
            "P-001",
            "Coca Cola",
            "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.suara.com%2Flifestyle%2F2021%2F06%2F17%2F133251%2Fmanfaat-coca-cola-pembersih-kloset-hingga-pengendali-hama&psig=AOvVaw1KvfydFb6yBI2r7wNBufL1&ust=1684563185952000&source=images&cd=vfe&ved=0CBEQjRxqFwoTCJivxITdgP8CFQAAAAAdAAAAABAE",
            "Coke",
            "Coca-Cola, or Coke, is a carbonated soft drink manufactured by the Coca-Cola Company. In 2013, Coke products were sold in over 200 countries worldwide, with consumers drinking more than 1.8 billion company beverage servings each day.",
            0,
            false,
        ),
        arrayOf(
            "P-002",
            "Aqua",
            "https://www.google.com/imgres?imgurl=https%3A%2F%2Fimages.k24klik.com%2Fproduct%2Fapotek_online_k24klik_20211206100212359225_AQUA-AIR-MINERAL-1500ML.jpg&tbnid=CbWBr7fFliyo8M&vet=12ahUKEwjiydPe3YD_AhU2ktgFHdVLBbYQMygbegUIARCiAg..i&imgrefurl=https%3A%2F%2Fwww.k24klik.com%2Fp%2Faqua-air-mineral-1500ml-28864&docid=MayICv1McG81hM&w=300&h=300&q=aqua&ved=2ahUKEwjiydPe3YD_AhU2ktgFHdVLBbYQMygbegUIARCiAg",
            "Mineral Water",
            "Air mineral AQUA berasal dari sumber mata air yang terpilih, serta memiliki Tiga Perlindungan, yaitu; melindungi ekosistem sumber airnya, menjaga kealamian mineralnya, serta diproses secara saksama untuk menjaga keasliannya sampai ke tangan anda.",
            1,
            false,
        ),
        arrayOf(
            "P-003",
            "NU Milk Tea",
            "https://img.priceza.co.id/img2/2130004/0001/2130004-20211117042123-817728574273306.jpg",
            "Milk Tea",
            "Nu Milk Tea merupakan minuman teh susu dalam kemasan pertama di Indonesia. Terbuat dari kombinasi tepat antara ekstrak daun teh premium dengan kekentalan susu yang nikmat, memberikan sensasi menyenangkan dan membantumu rileks menghadapi hari. Tanpa pengawet, pewarna, dan pemanis buatan sehingga aman untuk dikonsumsi.",
            2,
            false,
        ),
        arrayOf(
            "P-001",
            "Coca Cola",
            "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.suara.com%2Flifestyle%2F2021%2F06%2F17%2F133251%2Fmanfaat-coca-cola-pembersih-kloset-hingga-pengendali-hama&psig=AOvVaw1KvfydFb6yBI2r7wNBufL1&ust=1684563185952000&source=images&cd=vfe&ved=0CBEQjRxqFwoTCJivxITdgP8CFQAAAAAdAAAAABAE",
            "Coke",
            "Coca-Cola, or Coke, is a carbonated soft drink manufactured by the Coca-Cola Company. In 2013, Coke products were sold in over 200 countries worldwide, with consumers drinking more than 1.8 billion company beverage servings each day.",
            0,
            false,
        ),
        arrayOf(
            "P-002",
            "Aqua",
            "https://www.google.com/imgres?imgurl=https%3A%2F%2Fimages.k24klik.com%2Fproduct%2Fapotek_online_k24klik_20211206100212359225_AQUA-AIR-MINERAL-1500ML.jpg&tbnid=CbWBr7fFliyo8M&vet=12ahUKEwjiydPe3YD_AhU2ktgFHdVLBbYQMygbegUIARCiAg..i&imgrefurl=https%3A%2F%2Fwww.k24klik.com%2Fp%2Faqua-air-mineral-1500ml-28864&docid=MayICv1McG81hM&w=300&h=300&q=aqua&ved=2ahUKEwjiydPe3YD_AhU2ktgFHdVLBbYQMygbegUIARCiAg",
            "Mineral Water",
            "Air mineral AQUA berasal dari sumber mata air yang terpilih, serta memiliki Tiga Perlindungan, yaitu; melindungi ekosistem sumber airnya, menjaga kealamian mineralnya, serta diproses secara saksama untuk menjaga keasliannya sampai ke tangan anda.",
            1,
            true,
        ),
        arrayOf(
            "P-003",
            "NU Milk Tea",
            "https://img.priceza.co.id/img2/2130004/0001/2130004-20211117042123-817728574273306.jpg",
            "Milk Tea",
            "Nu Milk Tea merupakan minuman teh susu dalam kemasan pertama di Indonesia. Terbuat dari kombinasi tepat antara ekstrak daun teh premium dengan kekentalan susu yang nikmat, memberikan sensasi menyenangkan dan membantumu rileks menghadapi hari. Tanpa pengawet, pewarna, dan pemanis buatan sehingga aman untuk dikonsumsi.",
            2,
            false,
        ),
    )

    private var favoriteData = arrayOf(
        arrayOf(
            "P-001",
            "Coca Cola",
            "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.suara.com%2Flifestyle%2F2021%2F06%2F17%2F133251%2Fmanfaat-coca-cola-pembersih-kloset-hingga-pengendali-hama&psig=AOvVaw1KvfydFb6yBI2r7wNBufL1&ust=1684563185952000&source=images&cd=vfe&ved=0CBEQjRxqFwoTCJivxITdgP8CFQAAAAAdAAAAABAE",
            "Coke",
            "Coca-Cola, or Coke, is a carbonated soft drink manufactured by the Coca-Cola Company. In 2013, Coke products were sold in over 200 countries worldwide, with consumers drinking more than 1.8 billion company beverage servings each day.",
            0,
            true,
        ),
        arrayOf(
            "P-002",
            "Aqua",
            "https://www.google.com/imgres?imgurl=https%3A%2F%2Fimages.k24klik.com%2Fproduct%2Fapotek_online_k24klik_20211206100212359225_AQUA-AIR-MINERAL-1500ML.jpg&tbnid=CbWBr7fFliyo8M&vet=12ahUKEwjiydPe3YD_AhU2ktgFHdVLBbYQMygbegUIARCiAg..i&imgrefurl=https%3A%2F%2Fwww.k24klik.com%2Fp%2Faqua-air-mineral-1500ml-28864&docid=MayICv1McG81hM&w=300&h=300&q=aqua&ved=2ahUKEwjiydPe3YD_AhU2ktgFHdVLBbYQMygbegUIARCiAg",
            "Mineral Water",
            "Air mineral AQUA berasal dari sumber mata air yang terpilih, serta memiliki Tiga Perlindungan, yaitu; melindungi ekosistem sumber airnya, menjaga kealamian mineralnya, serta diproses secara saksama untuk menjaga keasliannya sampai ke tangan anda.",
            1,
            true,
        ),
        arrayOf(
            "P-003",
            "NU Milk Tea",
            "https://img.priceza.co.id/img2/2130004/0001/2130004-20211117042123-817728574273306.jpg",
            "Milk Tea",
            "Nu Milk Tea merupakan minuman teh susu dalam kemasan pertama di Indonesia. Terbuat dari kombinasi tepat antara ekstrak daun teh premium dengan kekentalan susu yang nikmat, memberikan sensasi menyenangkan dan membantumu rileks menghadapi hari. Tanpa pengawet, pewarna, dan pemanis buatan sehingga aman untuk dikonsumsi.",
            2,
            true,
        ),
    )

    val listData: ArrayList<Product>
        get() {
            val list = ArrayList<Product>()
            for (aData in data) {
                val product = Product()
                product.id = aData[0].toString()
                product.name = aData[1].toString()
                product.photoUrl = aData[2].toString()
                product.category = aData[3].toString()
                product.description = aData[4].toString()
                product.warningIndicator = aData[5] as Int
                product.isFavorite = aData[6] as Boolean
                list.add(product)
            }
            return list
        }

    val favoriteListData: ArrayList<Product>
        get() {
            val list = ArrayList<Product>()
            for (aData in favoriteData) {
                val product = Product()
                product.id = aData[0].toString()
                product.name = aData[1].toString()
                product.photoUrl = aData[2].toString()
                product.category = aData[3].toString()
                product.description = aData[4].toString()
                product.warningIndicator = aData[5] as Int
                product.isFavorite = aData[6] as Boolean
                list.add(product)
            }
            return list
        }
}