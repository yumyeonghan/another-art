<template>
    <div class="container">
        <div class="row">
            <div class="col-md-5">
                <div class="row">
                    <div class="col-md-12 offset-md-1 mt-5">
                        <img src="https://via.placeholder.com/470x305" class="w-100">
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12 offset-md-1 mt-2">
                        <a href="#"><img src="https://via.placeholder.com/90x60" class="mx-1"
                                style="width: 23.3%; height: 100px;"></a>
                        <a href="#"><img src="https://via.placeholder.com/90x60" class="mx-1"
                                style="width: 23.3%; height: 100px;"></a>
                        <a href="#"><img src="https://via.placeholder.com/90x60" class="mx-1"
                                style="width: 23.3%; height: 100px;"></a>
                        <a href="#"><img src="https://via.placeholder.com/90x60" class="mx-1"
                                style="width: 23.3%; height: 100px;"></a>
                    </div>
                </div>

            </div>

            <div class="col-lg-5 offset-lg-1 mt-5">
                <div class="s_product_text">

                    <div class="row g-3 mt-2">
                        <div :class="col_style" class="mb-2">
                            <h3>art3</h3>
                        </div>

                        <div :class="col_style" class="mb-2">
                            <h1>40,000원</h1>
                        </div>

                        <hr>

                        <div :class="col_style" class="mb-2">
                            <ul class="list-group list-group-horizontal" >
                                <li class="list-group-item" style="border: none;">
                                    <font-awesome-icon icon="fa-solid fa-heart" class="mx-1" />
                                    <a> {{ artwork.likes }}</a>
                                </li>
                                <li class="list-group-item" style="border: none;">
                                    <font-awesome-icon icon="fa-solid fa-eye" class="mx-1" />
                                    <a> {{ artwork.views }}</a>
                                </li>
                                <li class="list-group-item" style="border: none;">
                                    <font-awesome-icon icon="fa-solid fa-clock" class="mx-1"/>
                                    <a> {{ artwork.registerDate }}</a>
                                </li>
                            </ul>




                        </div>


                        <div :class="col_style" class="mb-2">
                            <p>
                                Mill Oil is an innovative oil filled radiator with the most modern technology. If you
                                are
                                looking for something that can make your interior look awesome, and at the same time.
                            </p>
                        </div>
                    </div>
                    <div class="card_area">
                        <div class="row g-3">
                            <div class="col-md-3">
                                <a href="#" class="btn btn-lg btn-outline-danger"
                                    style="border-radius: 6px; width: 120px;">
                                    <font-awesome-icon icon="fa-regular fa-heart" /> 찜
                                </a>
                            </div>
                            <div class="col-md-3">
                                <a href="#" class="btn btn-lg btn-outline-primary"
                                    style="border-radius: 6px; width: 120px;">바로구매</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: 'generalWorkPurchase',
    data() {
        return {
            artwork: {
                name: '',
                price: '',
                description: '',
                saleType: '',
                likes: 2,
                views: 46,
                registerDate: "2022-07-13",


            }
        }
    }
}
</script>

<style>
</style>