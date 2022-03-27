<template>
  <div>
      <HeaderMessage></HeaderMessage>
      <DynamicMainSection></DynamicMainSection>
  </div>
</template>

<script>
import HeaderMessage from '@/components/HeaderMessage'
import DynamicMainSection from '@/components/DynamicMainSection'
export default {
    name:'PersonPageHome',
    components:{
      HeaderMessage,
      DynamicMainSection,
  }
}
</script>

<style>

</style>