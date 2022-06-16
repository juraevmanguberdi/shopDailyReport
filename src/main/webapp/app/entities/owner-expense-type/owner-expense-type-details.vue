<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="ownerExpenseType">
        <h2 class="jh-entity-heading" data-cy="ownerExpenseTypeDetailsHeading">
          <span v-text="$t('shopDailyReportApp.ownerExpenseType.detail.title')">OwnerExpenseType</span> {{ ownerExpenseType.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('shopDailyReportApp.ownerExpenseType.name')">Name</span>
          </dt>
          <dd>
            <span>{{ ownerExpenseType.name }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopDailyReportApp.ownerExpenseType.code')">Code</span>
          </dt>
          <dd>
            <span>{{ ownerExpenseType.code }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopDailyReportApp.ownerExpenseType.notes')">Notes</span>
          </dt>
          <dd>
            <span>{{ ownerExpenseType.notes }}</span>
          </dd>
          <dt>
            <span v-text="$t('shopDailyReportApp.ownerExpenseType.createdDate')">Created Date</span>
          </dt>
          <dd>
            <span>{{ ownerExpenseType.createdDate }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="ownerExpenseType.id"
          :to="{ name: 'OwnerExpenseTypeEdit', params: { ownerExpenseTypeId: ownerExpenseType.id } }"
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

<script lang="ts" src="./owner-expense-type-details.component.ts"></script>
