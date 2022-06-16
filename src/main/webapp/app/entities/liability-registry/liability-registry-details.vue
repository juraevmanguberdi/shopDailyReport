<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="liabilityRegistry">
        <h2 class="jh-entity-heading" data-cy="liabilityRegistryDetailsHeading">
          <span v-text="$t('shopDailyReportApp.liabilityRegistry.detail.title')">LiabilityRegistry</span> {{ liabilityRegistry.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('shopDailyReportApp.liabilityRegistry.today')">Today</span>
          </dt>
          <dd>
            <span>{{ liabilityRegistry.today }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopDailyReportApp.liabilityRegistry.totalLiabilities')">Total Liabilities</span>
          </dt>
          <dd>
            <span>{{ liabilityRegistry.totalLiabilities }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopDailyReportApp.liabilityRegistry.supplier')">Supplier</span>
          </dt>
          <dd>
            <span>{{ liabilityRegistry.supplier }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopDailyReportApp.liabilityRegistry.bankLoan')">Bank Loan</span>
          </dt>
          <dd>
            <span>{{ liabilityRegistry.bankLoan }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopDailyReportApp.liabilityRegistry.other')">Other</span>
          </dt>
          <dd>
            <span>{{ liabilityRegistry.other }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopDailyReportApp.liabilityRegistry.code')">Code</span>
          </dt>
          <dd>
            <span>{{ liabilityRegistry.code }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopDailyReportApp.liabilityRegistry.notes')">Notes</span>
          </dt>
          <dd>
            <span>{{ liabilityRegistry.notes }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="liabilityRegistry.id"
          :to="{ name: 'LiabilityRegistryEdit', params: { liabilityRegistryId: liabilityRegistry.id } }"
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

<script lang="ts" src="./liability-registry-details.component.ts"></script>
