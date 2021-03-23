<template>
  <div class="container">
    <div class="row justify-content-sm-center">
      <div class="col">
        <div class="lds-dual-ring"></div>
      </div>
    </div>
  </div>
</template>

<style>
.lds-dual-ring {
  display: inline-block;
  width: 80px;
  height: 80px;
}

.lds-dual-ring:after {
  content: " ";
  display: block;
  width: 64px;
  height: 64px;
  margin: 8px;
  border-radius: 50%;
  border: 6px solid #203;
  border-color: #203 transparent #203 transparent;
  animation: lds-dual-ring 1.2s linear infinite;
}

@keyframes lds-dual-ring {
  0% {
    transform: rotate(0deg);
  }
  100% {
    transform: rotate(360deg);
  }
}
</style>