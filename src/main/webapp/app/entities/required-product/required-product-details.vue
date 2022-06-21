<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="requiredProduct">
        <h2 class="jh-entity-heading" data-cy="requiredProductDetailsHeading">
          <span v-text="$t('shopDailyReportApp.requiredProduct.detail.title')">RequiredProduct</span> {{ requiredProduct.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('shopDailyReportApp.requiredProduct.name')">Name</span>
          </dt>
          <dd>
            <span>{{ requiredProduct.name }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopDailyReportApp.requiredProduct.note')">Note</span>
          </dt>
          <dd>
            <span>{{ requiredProduct.note }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopDailyReportApp.requiredProduct.code')">Code</span>
          </dt>
          <dd>
            <span>{{ requiredProduct.code }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopDailyReportApp.requiredProduct.requiredProductType')">Required Product Type</span>
          </dt>
          <dd>
            <div v-if="requiredProduct.requiredProductType">
              <router-link
                :to="{ name: 'RequiredProductTypeView', params: { requiredProductTypeId: requiredProduct.requiredProductType.id } }"
                >{{ requiredProduct.requiredProductType.id }}</router-link
              >
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="requiredProduct.id"
          :to="{ name: 'RequiredProductEdit', params: { requiredProductId: requiredProduct.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./required-product-details.component.ts"></script>
