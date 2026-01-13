package com.example.projecttest

fun res_idToName(ID : Int) : String{   //가게 id를 가게 이름으로 변환
    var name : String = ""
    if(ID == 1)
        name = "파스타"
    else if(ID == 2)
        name = "마라탕"
    else if(ID == 3)
        name = "군산카츠"
    else if(ID == 4)
        name = "마성떡볶이"
    else if(ID == 5)
        name = "오늘의메뉴A"
    else if(ID == 6)
        name = "오늘의메뉴B"
    return name
}

fun res_NameToId(name : String?) : Int {  //가게 이름을 id로 변환
    var id : Int = 0
    if(name == "파스타")
        id = 1
    else if(name == "마라탕")
        id = 2
    else if(name == "군산카츠")
        id = 3
    else if(name == "마성떡볶이")
        id = 4
    else if(name == "오늘의메뉴A")
        id = 5
    else if(name == "오늘의메뉴B")
        id = 6
    return id
}

fun res_idToImage(ID : Int) : Int {  //가게 id를 가게 이미지로 변환
    var drawableId  = R.drawable.pasta_img_radius
    if(ID == 2)
        drawableId = R.drawable.malatang_img_radius
    else if(ID == 3)
        drawableId = R.drawable.don_img_radius
    else if(ID == 4)
        drawableId = R.drawable.tteokbokki_img_radius
    else if(ID == 5)
        drawableId = R.drawable.todaymenu_img_radius
    else if(ID == 6)
        drawableId = R.drawable.todaymenu_img_radius
    return drawableId
}

fun menuToPrice(rest : Int, menu : String) : Int {   //가게와 메뉴를 넣으면 가격으로 변환
    var price = 0
    if(rest == 1) {
        if(menu == "고기리들기름파스타")
            price = 6000
        else if(menu == "우삼겹알리올리오")
            price = 6500
        else if(menu == "클래식토마토파스타")
            price = 6500
        else if(menu == "트러플버섯크림파스타")
            price = 6500
        else if(menu == "4분돼지김치파스타")
            price = 7500
        else if(menu == "대패삼겹크림파스타")
            price = 7500
        else if(menu == "매콤로제파스타")
            price = 7500
        else if(menu == "김치삼겹필라프")
            price = 7500
        else if(menu == "콜라(500ml)")
            price = 1500
        else if(menu == "사이다(500ml)")
            price = 1500
    } else if(rest == 2) {  //마라탕
        if(menu == "한우사골마라탕")
            price = 6900
        if(menu == "마라샹궈")
            price = 8700
        if(menu == "꿔바로우(소)")
            price = 5000
        if(menu == "꿔바로우(대)")
            price = 10000
        if(menu == "빙홍차")
            price = 2500
    } else if(rest == 3) {  //군산카츠
        if(menu == "카레덮밥")
            price = 4900
        if(menu == "고구마치즈돈까스")
            price = 6900
        if(menu == "돈카츠카레덮밥")
            price = 7500
        if(menu == "새우카레덮밥")
            price = 7500
        if(menu == "더블돈카츠")
            price = 9500
    } else if(rest == 4) {  //마성떡볶이
        if(menu == "마성떡볶이")
            price = 3500
        if(menu == "마성라면")
            price = 3500
        if(menu == "부산어묵")
            price = 2000
        if(menu == "찰순대")
            price = 3500
        if(menu == "치킨마요덮밥")
            price = 5000
        if(menu == "마성김밥")
            price = 3000
        if(menu == "삼각잡채말이만두")
            price = 2000
        if(menu == "모듬튀김")
            price = 6500
    } else if(rest == 5) {
        price = 6500
    } else if(rest == 6) {
        price = 6500
    }
    return price
}

fun menuTores_id(menu : String) : Int{   //메뉴를 넣으면 가게 id로 변환
    var id = 4
    if(menu == "고기리들기름파스타")
        id = 1
    else if(menu == "우삼겹알리올리오")
        id = 1
    else if(menu == "클래식토마토파스타")
        id = 1
    else if(menu == "트러플버섯크림파스타")
        id = 1
    else if(menu == "4분돼지김치파스타")
        id = 1
    else if(menu == "대패삼겹크림파스타")
        id = 1
    else if(menu == "매콤로제파스타")
        id = 1
    else if(menu == "김치삼겹필라프")
        id = 1
    else if(menu == "콜라(500ml)")
        id = 1
    else if(menu == "사이다(500ml)")
        id = 1
    else if(menu == "오늘의메뉴A")
        id = 5
    else if(menu == "오늘의메뉴B")
        id = 6
    else if(menu == "카레덮밥")
        id = 3
    else if(menu == "고구마치즈돈까스")
        id = 3
    else if(menu == "돈카츠카레덮밥")
        id = 3
    else if(menu == "새우카레덮밥")
        id = 3
    else if(menu == "더블돈카츠")
        id = 3
    else if(menu == "한우사골마라탕")
        id = 2
    else if(menu == "마라샹궈")
        id = 2
    else if(menu == "꿔바로우(소)")
        id = 2
    else if(menu == "꿔바로우(대)")
        id = 2
    else if(menu == "빙홍차")
        id = 2
    return id
}