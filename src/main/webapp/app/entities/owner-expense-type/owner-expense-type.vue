<template>
  <div>
    <h2 id="page-heading" data-cy="OwnerExpenseTypeHeading">
      <span v-text="$t('shopDailyReportApp.ownerExpenseType.home.title')" id="owner-expense-type-heading">Owner Expense Types</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('shopDailyReportApp.ownerExpenseType.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'OwnerExpenseTypeCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-owner-expense-type"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('shopDailyReportApp.ownerExpenseType.home.createLabel')"> Create a new Owner Expense Type </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && ownerExpenseTypes && ownerExpenseTypes.length === 0">
      <span v-text="$t('shopDailyReportApp.ownerExpenseType.home.notFound')">No ownerExpenseTypes found</span>
    </div>
    <div class="table-responsive" v-if="ownerExpenseTypes && ownerExpenseTypes.length > 0">
      <table class="table table-striped" aria-describedby="ownerExpenseTypes">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="$t('global.field.id')">ID</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('name')">
              <span v-text="$t('shopDailyReportApp.ownerExpenseType.name')">Name</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'name'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('code')">
              <span v-text="$t('shopDailyReportApp.ownerExpenseType.code')">Code</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'code'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('notes')">
              <span v-text="$t('shopDailyReportApp.ownerExpenseType.notes')">Notes</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'notes'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('createdDate')">
              <span v-text="$t('shopDailyReportApp.ownerExpenseType.createdDate')">Created Date</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'createdDate'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="ownerExpenseType in ownerExpenseTypes" :key="ownerExpenseType.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'OwnerExpenseTypeView', params: { ownerExpenseTypeId: ownerExpenseType.id } }">{{
                ownerExpenseType.id
              }}</router-link>
            </td>
            <td>{{ ownerExpenseType.name }}</td>
            <td>{{ ownerExpenseType.code }}</td>
            <td>{{ ownerExpenseType.notes }}</td>
            <td>{{ ownerExpenseType.createdDate }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'OwnerExpenseTypeView', params: { ownerExpenseTypeId: ownerExpenseType.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'OwnerExpenseTypeEdit', params: { ownerExpenseTypeId: ownerExpenseType.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(ownerExpenseType)"
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
          id="shopDailyReportApp.ownerExpenseType.delete.question"
          data-cy="ownerExpenseTypeDeleteDialogHeading"
          v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-ownerExpenseType-heading" v-text="$t('shopDailyReportApp.ownerExpenseType.delete.question', { id: removeId })">
          Are you sure you want to delete this Owner Expense Type?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-ownerExpenseType"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeOwnerExpenseType()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="ownerExpenseTypes && ownerExpenseTypes.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./owner-expense-type.component.ts"></script>
