<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="assetRegistry">
        <h2 class="jh-entity-heading" data-cy="assetRegistryDetailsHeading">
          <span v-text="$t('shopDailyReportApp.assetRegistry.detail.title')">AssetRegistry</span> {{ assetRegistry.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('shopDailyReportApp.assetRegistry.today')">Today</span>
          </dt>
          <dd>
            <span>{{ assetRegistry.today }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopDailyReportApp.assetRegistry.totalAssets')">Total Assets</span>
          </dt>
          <dd>
            <span>{{ assetRegistry.totalAssets }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopDailyReportApp.assetRegistry.currentAssets')">Current Assets</span>
          </dt>
          <dd>
            <span>{{ assetRegistry.currentAssets }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopDailyReportApp.assetRegistry.moneyTotal')">Money Total</span>
          </dt>
          <dd>
            <span>{{ assetRegistry.moneyTotal }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopDailyReportApp.assetRegistry.cashShop')">Cash Shop</span>
          </dt>
          <dd>
            <span>{{ assetRegistry.cashShop }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopDailyReportApp.assetRegistry.cashOwner')">Cash Owner</span>
          </dt>
          <dd>
            <span>{{ assetRegistry.cashOwner }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopDailyReportApp.assetRegistry.bankAccount')">Bank Account</span>
          </dt>
          <dd>
            <span>{{ assetRegistry.bankAccount }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopDailyReportApp.assetRegistry.goods')">Goods</span>
          </dt>
          <dd>
            <span>{{ assetRegistry.goods }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopDailyReportApp.assetRegistry.debitor')">Debitor</span>
          </dt>
          <dd>
            <span>{{ assetRegistry.debitor }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopDailyReportApp.assetRegistry.longTermAssets')">Long Term Assets</span>
          </dt>
          <dd>
            <span>{{ assetRegistry.longTermAssets }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopDailyReportApp.assetRegistry.transport')">Transport</span>
          </dt>
          <dd>
            <span>{{ assetRegistry.transport }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopDailyReportApp.assetRegistry.equipment')">Equipment</span>
          </dt>
          <dd>
            <span>{{ assetRegistry.equipment }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopDailyReportApp.assetRegistry.realEstate')">Real Estate</span>
          </dt>
          <dd>
            <span>{{ assetRegistry.realEstate }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopDailyReportApp.assetRegistry.other')">Other</span>
          </dt>
          <dd>
            <span>{{ assetRegistry.other }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopDailyReportApp.assetRegistry.code')">Code</span>
          </dt>
          <dd>
            <span>{{ assetRegistry.code }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopDailyReportApp.assetRegistry.notes')">Notes</span>
          </dt>
          <dd>
            <span>{{ assetRegistry.notes }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="assetRegistry.id"
          :to="{ name: 'AssetRegistryEdit', params: { assetRegistryId: assetRegistry.id } }"
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

<script lang="ts" src="./asset-registry-details.component.ts"></script>
