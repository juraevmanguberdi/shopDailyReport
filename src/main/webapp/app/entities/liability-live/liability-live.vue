<template>
  <div>
    <h2 id="page-heading" data-cy="LiabilityLiveHeading">
      <span v-text="$t('shopDailyReportApp.liabilityLive.home.title')" id="liability-live-heading">Liability Lives</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('shopDailyReportApp.liabilityLive.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'LiabilityLiveCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-liability-live"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('shopDailyReportApp.liabilityLive.home.createLabel')"> Create a new Liability Live </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && liabilityLives && liabilityLives.length === 0">
      <span v-text="$t('shopDailyReportApp.liabilityLive.home.notFound')">No liabilityLives found</span>
    </div>
    <div class="table-responsive" v-if="liabilityLives && liabilityLives.length > 0">
      <table class="table table-striped" aria-describedby="liabilityLives">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="$t('global.field.id')">ID</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('totalLiabilities')">
              <span v-text="$t('shopDailyReportApp.liabilityLive.totalLiabilities')">Total Liabilities</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'totalLiabilities'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('supplier')">
              <span v-text="$t('shopDailyReportApp.liabilityLive.supplier')">Supplier</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'supplier'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('bankLoan')">
              <span v-text="$t('shopDailyReportApp.liabilityLive.bankLoan')">Bank Loan</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'bankLoan'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('other')">
              <span v-text="$t('shopDailyReportApp.liabilityLive.other')">Other</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'other'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('code')">
              <span v-text="$t('shopDailyReportApp.liabilityLive.code')">Code</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'code'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('notes')">
              <span v-text="$t('shopDailyReportApp.liabilityLive.notes')">Notes</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'notes'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="liabilityLive in liabilityLives" :key="liabilityLive.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'LiabilityLiveView', params: { liabilityLiveId: liabilityLive.id } }">{{
                liabilityLive.id
              }}</router-link>
            </td>
            <td>{{ liabilityLive.totalLiabilities }}</td>
            <td>{{ liabilityLive.supplier }}</td>
            <td>{{ liabilityLive.bankLoan }}</td>
            <td>{{ liabilityLive.other }}</td>
            <td>{{ liabilityLive.code }}</td>
            <td>{{ liabilityLive.notes }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'LiabilityLiveView', params: { liabilityLiveId: liabilityLive.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'LiabilityLiveEdit', params: { liabilityLiveId: liabilityLive.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(liabilityLive)"
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
          id="shopDailyReportApp.liabilityLive.delete.question"
          data-cy="liabilityLiveDeleteDialogHeading"
          v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-liabilityLive-heading" v-text="$t('shopDailyReportApp.liabilityLive.delete.question', { id: removeId })">
          Are you sure you want to delete this Liability Live?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-liabilityLive"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeLiabilityLive()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="liabilityLives && liabilityLives.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./liability-live.component.ts"></script>
