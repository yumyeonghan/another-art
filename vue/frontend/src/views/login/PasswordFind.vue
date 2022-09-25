<template>
    <div id="wrapper" class="container-fluid">
        <div class="container px-4s my-5">
            <div class="row gx-4 justify-content-center m-auto">
                <div class="col-md-10 position-static d-block p-3 text-black"
                    style="--bs-bg-opacity: .4; --bs-text-opacity: 0.6;">

                    <div class="p-4">
                        <h4 class="mb-3 text-center text-black fs-3" style="--bs-text-opacity: 0.6;">아이디 찾기</h4>
                    </div>
                    <!-- @submit.prevent="submitForm" -->
                    <form action="/" methods="post" class="needs-validation" novalidate>
                        <div class="row g-3">

                            <div class="col-md-6 offset-md-3">
                                <input type="text" class="form-control form-control-lg p-3" id="id" name="id"
                                    placeholder="아이디" required>
                            </div>

                            <div class="col-md-6 offset-md-3">
                                <input type="text" class="form-control form-control-lg p-3" id="name" name="name"
                                    placeholder="이름" required>
                            </div>

                            <div class="col-md-6 offset-md-3">
                                <input type="email" class="form-control form-control-lg p-3" id="email" name="email"
                                    placeholder="이메일" required>
                            </div>

                        </div>

                        <div class="row">
                            <hr class="my-4 col-md-12 offset-md-0 border border-1 border-dark" style="opacity: 0.1;">
                        </div>

                        <div class="row g-3">
                            <button class="btn btn-outline-dark btn-lg col-md-6 offset-md-3 p-3" style="opacity: 0.7;"
                                type="submit" id="signup">완료</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: 'passwordFind',
}
</script>

<style>
</style>