<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="assetLive">
        <h2 class="jh-entity-heading" data-cy="assetLiveDetailsHeading">
          <span v-text="$t('shopDailyReportApp.assetLive.detail.title')">AssetLive</span> {{ assetLive.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('shopDailyReportApp.assetLive.totalAssets')">Total Assets</span>
          </dt>
          <dd>
            <span>{{ assetLive.totalAssets }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopDailyReportApp.assetLive.currentAssets')">Current Assets</span>
          </dt>
          <dd>
            <span>{{ assetLive.currentAssets }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopDailyReportApp.assetLive.moneyTotal')">Money Total</span>
          </dt>
          <dd>
            <span>{{ assetLive.moneyTotal }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopDailyReportApp.assetLive.cashShop')">Cash Shop</span>
          </dt>
          <dd>
            <span>{{ assetLive.cashShop }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopDailyReportApp.assetLive.cashOwner')">Cash Owner</span>
          </dt>
          <dd>
            <span>{{ assetLive.cashOwner }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopDailyReportApp.assetLive.bankAccount')">Bank Account</span>
          </dt>
          <dd>
            <span>{{ assetLive.bankAccount }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopDailyReportApp.assetLive.goods')">Goods</span>
          </dt>
          <dd>
            <span>{{ assetLive.goods }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopDailyReportApp.assetLive.debitor')">Debitor</span>
          </dt>
          <dd>
            <span>{{ assetLive.debitor }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopDailyReportApp.assetLive.longTermAssets')">Long Term Assets</span>
          </dt>
          <dd>
            <span>{{ assetLive.longTermAssets }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopDailyReportApp.assetLive.transport')">Transport</span>
          </dt>
          <dd>
            <span>{{ assetLive.transport }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopDailyReportApp.assetLive.equipment')">Equipment</span>
          </dt>
          <dd>
            <span>{{ assetLive.equipment }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopDailyReportApp.assetLive.realEstate')">Real Estate</span>
          </dt>
          <dd>
            <span>{{ assetLive.realEstate }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopDailyReportApp.assetLive.other')">Other</span>
          </dt>
          <dd>
            <span>{{ assetLive.other }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopDailyReportApp.assetLive.code')">Code</span>
          </dt>
          <dd>
            <span>{{ assetLive.code }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopDailyReportApp.assetLive.notes')">Notes</span>
          </dt>
          <dd>
            <span>{{ assetLive.notes }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="assetLive.id"
          :to="{ name: 'AssetLiveEdit', params: { assetLiveId: assetLive.id } }"
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

<script lang="ts" src="./asset-live-details.component.ts"></script>
