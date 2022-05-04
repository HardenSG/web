<template>
  <div>
      <section>
        <el-carousel :interval="5000" arrow="always" > 
          <el-carousel-item v-for="item in 4" :key="item">
            <h3>
                <img src="../assets/preview.jpg" />
            </h3>
          </el-carousel-item>
        </el-carousel>
      </section>
  </div>
</template>

<script>
export default {
    name:'ImgSlide'
}
</script>

<style>

</style>