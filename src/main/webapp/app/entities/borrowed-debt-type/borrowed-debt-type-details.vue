<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="borrowedDebtType">
        <h2 class="jh-entity-heading" data-cy="borrowedDebtTypeDetailsHeading">
          <span v-text="$t('shopDailyReportApp.borrowedDebtType.detail.title')">BorrowedDebtType</span> {{ borrowedDebtType.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('shopDailyReportApp.borrowedDebtType.name')">Name</span>
          </dt>
          <dd>
            <span>{{ borrowedDebtType.name }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopDailyReportApp.borrowedDebtType.code')">Code</span>
          </dt>
          <dd>
            <span>{{ borrowedDebtType.code }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopDailyReportApp.borrowedDebtType.notes')">Notes</span>
          </dt>
          <dd>
            <span>{{ borrowedDebtType.notes }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopDailyReportApp.borrowedDebtType.createdDate')">Created Date</span>
          </dt>
          <dd>
            <span>{{ borrowedDebtType.createdDate }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="borrowedDebtType.id"
          :to="{ name: 'BorrowedDebtTypeEdit', params: { borrowedDebtTypeId: borrowedDebtType.id } }"
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

<script lang="ts" src="./borrowed-debt-type-details.component.ts"></script>
