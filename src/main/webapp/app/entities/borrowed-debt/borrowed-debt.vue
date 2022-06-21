<template>
  <div>
    <h2 id="page-heading" data-cy="BorrowedDebtHeading">
      <span v-text="$t('shopDailyReportApp.borrowedDebt.home.title')" id="borrowed-debt-heading">Borrowed Debts</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('shopDailyReportApp.borrowedDebt.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'BorrowedDebtCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-borrowed-debt"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('shopDailyReportApp.borrowedDebt.home.createLabel')"> Create a new Borrowed Debt </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && borrowedDebts && borrowedDebts.length === 0">
      <span v-text="$t('shopDailyReportApp.borrowedDebt.home.notFound')">No borrowedDebts found</span>
    </div>
    <div class="table-responsive" v-if="borrowedDebts && borrowedDebts.length > 0">
      <table class="table table-striped" aria-describedby="borrowedDebts">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="$t('global.field.id')">ID</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('amount')">
              <span v-text="$t('shopDailyReportApp.borrowedDebt.amount')">Amount</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'amount'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('notes')">
              <span v-text="$t('shopDailyReportApp.borrowedDebt.notes')">Notes</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'notes'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('code')">
              <span v-text="$t('shopDailyReportApp.borrowedDebt.code')">Code</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'code'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('date')">
              <span v-text="$t('shopDailyReportApp.borrowedDebt.date')">Date</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'date'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('borrowedDebtType.id')">
              <span v-text="$t('shopDailyReportApp.borrowedDebt.borrowedDebtType')">Borrowed Debt Type</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'borrowedDebtType.id'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="borrowedDebt in borrowedDebts" :key="borrowedDebt.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'BorrowedDebtView', params: { borrowedDebtId: borrowedDebt.id } }">{{
                borrowedDebt.id
              }}</router-link>
            </td>
            <td>{{ borrowedDebt.amount }}</td>
            <td>{{ borrowedDebt.notes }}</td>
            <td>{{ borrowedDebt.code }}</td>
            <td>{{ borrowedDebt.date }}</td>
            <td>
              <div v-if="borrowedDebt.borrowedDebtType">
                <router-link :to="{ name: 'BorrowedDebtTypeView', params: { borrowedDebtTypeId: borrowedDebt.borrowedDebtType.id } }">{{
                  borrowedDebt.borrowedDebtType.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'BorrowedDebtView', params: { borrowedDebtId: borrowedDebt.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'BorrowedDebtEdit', params: { borrowedDebtId: borrowedDebt.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(borrowedDebt)"
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
          id="shopDailyReportApp.borrowedDebt.delete.question"
          data-cy="borrowedDebtDeleteDialogHeading"
          v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-borrowedDebt-heading" v-text="$t('shopDailyReportApp.borrowedDebt.delete.question', { id: removeId })">
          Are you sure you want to delete this Borrowed Debt?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-borrowedDebt"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeBorrowedDebt()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="borrowedDebts && borrowedDebts.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./borrowed-debt.component.ts"></script>
