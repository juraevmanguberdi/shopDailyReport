<template>
  <div>
    <h2 id="page-heading" data-cy="OwnerExpenseHeading">
      <span v-text="$t('shopDailyReportApp.ownerExpense.home.title')" id="owner-expense-heading">Owner Expenses</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('shopDailyReportApp.ownerExpense.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'OwnerExpenseCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-owner-expense"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('shopDailyReportApp.ownerExpense.home.createLabel')"> Create a new Owner Expense </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && ownerExpenses && ownerExpenses.length === 0">
      <span v-text="$t('shopDailyReportApp.ownerExpense.home.notFound')">No ownerExpenses found</span>
    </div>
    <div class="table-responsive" v-if="ownerExpenses && ownerExpenses.length > 0">
      <table class="table table-striped" aria-describedby="ownerExpenses">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="$t('global.field.id')">ID</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('amount')">
              <span v-text="$t('shopDailyReportApp.ownerExpense.amount')">Amount</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'amount'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('code')">
              <span v-text="$t('shopDailyReportApp.ownerExpense.code')">Code</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'code'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('expenseDate')">
              <span v-text="$t('shopDailyReportApp.ownerExpense.expenseDate')">Expense Date</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'expenseDate'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('notes')">
              <span v-text="$t('shopDailyReportApp.ownerExpense.notes')">Notes</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'notes'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('ownerExpenseType.id')">
              <span v-text="$t('shopDailyReportApp.ownerExpense.ownerExpenseType')">Owner Expense Type</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'ownerExpenseType.id'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="ownerExpense in ownerExpenses" :key="ownerExpense.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'OwnerExpenseView', params: { ownerExpenseId: ownerExpense.id } }">{{
                ownerExpense.id
              }}</router-link>
            </td>
            <td>{{ ownerExpense.amount }}</td>
            <td>{{ ownerExpense.code }}</td>
            <td>{{ ownerExpense.expenseDate }}</td>
            <td>{{ ownerExpense.notes }}</td>
            <td>
              <div v-if="ownerExpense.ownerExpenseType">
                <router-link :to="{ name: 'OwnerExpenseTypeView', params: { ownerExpenseTypeId: ownerExpense.ownerExpenseType.id } }">{{
                  ownerExpense.ownerExpenseType.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'OwnerExpenseView', params: { ownerExpenseId: ownerExpense.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'OwnerExpenseEdit', params: { ownerExpenseId: ownerExpense.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(ownerExpense)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span
          id="shopDailyReportApp.ownerExpense.delete.question"
          data-cy="ownerExpenseDeleteDialogHeading"
          v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-ownerExpense-heading" v-text="$t('shopDailyReportApp.ownerExpense.delete.question', { id: removeId })">
          Are you sure you want to delete this Owner Expense?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-ownerExpense"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeOwnerExpense()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="ownerExpenses && ownerExpenses.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./owner-expense.component.ts"></script>
