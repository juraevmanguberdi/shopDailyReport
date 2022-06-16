<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="liabilityLive">
        <h2 class="jh-entity-heading" data-cy="liabilityLiveDetailsHeading">
          <span v-text="$t('shopDailyReportApp.liabilityLive.detail.title')">LiabilityLive</span> {{ liabilityLive.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('shopDailyReportApp.liabilityLive.totalLiabilities')">Total Liabilities</span>
          </dt>
          <dd>
            <span>{{ liabilityLive.totalLiabilities }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopDailyReportApp.liabilityLive.supplier')">Supplier</span>
          </dt>
          <dd>
            <span>{{ liabilityLive.supplier }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopDailyReportApp.liabilityLive.bankLoan')">Bank Loan</span>
          </dt>
          <dd>
            <span>{{ liabilityLive.bankLoan }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopDailyReportApp.liabilityLive.other')">Other</span>
          </dt>
          <dd>
            <span>{{ liabilityLive.other }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopDailyReportApp.liabilityLive.code')">Code</span>
          </dt>
          <dd>
            <span>{{ liabilityLive.code }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopDailyReportApp.liabilityLive.notes')">Notes</span>
          </dt>
          <dd>
            <span>{{ liabilityLive.notes }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="liabilityLive.id"
          :to="{ name: 'LiabilityLiveEdit', params: { liabilityLiveId: liabilityLive.id } }"
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

<script lang="ts" src="./liability-live-details.component.ts"></script>
