<template>
    <div>
        <div class="row g-3">
            <div :class="col_style" class="mb-2">
                <label for="name" class="form-label">작품명</label>
                <input type="text" class="form-control form-control-lg p-3" id="name" name="name" placeholder=""
                    value="" required>
            </div>

            <div :class="col_style" class="mb-2">
                <label for="tag" class="form-label">태그</label>
                <input type="text" class="form-control form-control-lg p-3" id="tag" name="tag"
                    placeholder="#산뜻한 #차가운 #심오한" value="" required>
            </div>

            <div :class="col_style" class="mb-2">
                <div class="row">
                    <label for="price" class="form-label">가격</label>
                    <div class="col-md-1 px-3 pt-3 text-center">
                        <h4 class="fw-lighter">₩</h4>
                    </div>
                    <div class="col-md-11">
                        <input type="number" class="form-control form-control-lg p-3" id="price" name="price" placeholder=""
                        value="" required>
                    </div>
                    <div class="col-md-4">
                    </div>
                </div>
            </div>

            <div :class="col_style" class="mb-2">
                <label for="description" class="form-label">상세 설명</label>
                <div class="row">
                    <div class="col-md-12">
                        <div class="form-floating">
                            <textarea class="form-control p-3" style="height: 145px; resize: none;"
                                placeholder="Leave a comment here" id="description" name="description"
                                required></textarea>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: 'generalWork',
    data() {
    return {
      col_style: 'col-md-10 offset-md-1'
    }
  },
}
</script>

<style>
</style>