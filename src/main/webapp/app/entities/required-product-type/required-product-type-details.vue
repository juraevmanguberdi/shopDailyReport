<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="requiredProductType">
        <h2 class="jh-entity-heading" data-cy="requiredProductTypeDetailsHeading">
          <span v-text="$t('shopDailyReportApp.requiredProductType.detail.title')">RequiredProductType</span> {{ requiredProductType.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('shopDailyReportApp.requiredProductType.name')">Name</span>
          </dt>
          <dd>
            <span>{{ requiredProductType.name }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopDailyReportApp.requiredProductType.code')">Code</span>
          </dt>
          <dd>
            <span>{{ requiredProductType.code }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopDailyReportApp.requiredProductType.notes')">Notes</span>
          </dt>
          <dd>
            <span>{{ requiredProductType.notes }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="requiredProductType.id"
          :to="{ name: 'RequiredProductTypeEdit', params: { requiredProductTypeId: requiredProductType.id } }"
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

<script lang="ts" src="./required-product-type-details.component.ts"></script>
