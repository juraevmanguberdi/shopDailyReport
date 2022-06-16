<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="dailyRegistryShop">
        <h2 class="jh-entity-heading" data-cy="dailyRegistryShopDetailsHeading">
          <span v-text="$t('shopDailyReportApp.dailyRegistryShop.detail.title')">DailyRegistryShop</span> {{ dailyRegistryShop.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('shopDailyReportApp.dailyRegistryShop.today')">Today</span>
          </dt>
          <dd>
            <span>{{ dailyRegistryShop.today }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopDailyReportApp.dailyRegistryShop.sales')">Sales</span>
          </dt>
          <dd>
            <span>{{ dailyRegistryShop.sales }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopDailyReportApp.dailyRegistryShop.goods')">Goods</span>
          </dt>
          <dd>
            <span>{{ dailyRegistryShop.goods }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopDailyReportApp.dailyRegistryShop.cashShop')">Cash Shop</span>
          </dt>
          <dd>
            <span>{{ dailyRegistryShop.cashShop }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopDailyReportApp.dailyRegistryShop.click')">Click</span>
          </dt>
          <dd>
            <span>{{ dailyRegistryShop.click }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopDailyReportApp.dailyRegistryShop.terminal')">Terminal</span>
          </dt>
          <dd>
            <span>{{ dailyRegistryShop.terminal }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopDailyReportApp.dailyRegistryShop.debtReturn')">Debt Return</span>
          </dt>
          <dd>
            <span>{{ dailyRegistryShop.debtReturn }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopDailyReportApp.dailyRegistryShop.debtGiven')">Debt Given</span>
          </dt>
          <dd>
            <span>{{ dailyRegistryShop.debtGiven }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopDailyReportApp.dailyRegistryShop.expense')">Expense</span>
          </dt>
          <dd>
            <span>{{ dailyRegistryShop.expense }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopDailyReportApp.dailyRegistryShop.balance')">Balance</span>
          </dt>
          <dd>
            <span>{{ dailyRegistryShop.balance }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopDailyReportApp.dailyRegistryShop.code')">Code</span>
          </dt>
          <dd>
            <span>{{ dailyRegistryShop.code }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="dailyRegistryShop.id"
          :to="{ name: 'DailyRegistryShopEdit', params: { dailyRegistryShopId: dailyRegistryShop.id } }"
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

<script lang="ts" src="./daily-registry-shop-details.component.ts"></script>
