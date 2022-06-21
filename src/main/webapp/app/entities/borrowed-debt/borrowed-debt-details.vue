<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="borrowedDebt">
        <h2 class="jh-entity-heading" data-cy="borrowedDebtDetailsHeading">
          <span v-text="$t('shopDailyReportApp.borrowedDebt.detail.title')">BorrowedDebt</span> {{ borrowedDebt.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('shopDailyReportApp.borrowedDebt.amount')">Amount</span>
          </dt>
          <dd>
            <span>{{ borrowedDebt.amount }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopDailyReportApp.borrowedDebt.notes')">Notes</span>
          </dt>
          <dd>
            <span>{{ borrowedDebt.notes }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopDailyReportApp.borrowedDebt.code')">Code</span>
          </dt>
          <dd>
            <span>{{ borrowedDebt.code }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopDailyReportApp.borrowedDebt.date')">Date</span>
          </dt>
          <dd>
            <span>{{ borrowedDebt.date }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopDailyReportApp.borrowedDebt.borrowedDebtType')">Borrowed Debt Type</span>
          </dt>
          <dd>
            <div v-if="borrowedDebt.borrowedDebtType">
              <router-link :to="{ name: 'BorrowedDebtTypeView', params: { borrowedDebtTypeId: borrowedDebt.borrowedDebtType.id } }">{{
                borrowedDebt.borrowedDebtType.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="borrowedDebt.id"
          :to="{ name: 'BorrowedDebtEdit', params: { borrowedDebtId: borrowedDebt.id } }"
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

<script lang="ts" src="./borrowed-debt-details.component.ts"></script>
