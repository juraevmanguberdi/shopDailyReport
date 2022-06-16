<template>
  <div>
    <h2 id="page-heading" data-cy="AssetLiveHeading">
      <span v-text="$t('shopDailyReportApp.assetLive.home.title')" id="asset-live-heading">Asset Lives</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('shopDailyReportApp.assetLive.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'AssetLiveCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-asset-live"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('shopDailyReportApp.assetLive.home.createLabel')"> Create a new Asset Live </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && assetLives && assetLives.length === 0">
      <span v-text="$t('shopDailyReportApp.assetLive.home.notFound')">No assetLives found</span>
    </div>
    <div class="table-responsive" v-if="assetLives && assetLives.length > 0">
      <table class="table table-striped" aria-describedby="assetLives">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="$t('global.field.id')">ID</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('totalAssets')">
              <span v-text="$t('shopDailyReportApp.assetLive.totalAssets')">Total Assets</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'totalAssets'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('currentAssets')">
              <span v-text="$t('shopDailyReportApp.assetLive.currentAssets')">Current Assets</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'currentAssets'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('moneyTotal')">
              <span v-text="$t('shopDailyReportApp.assetLive.moneyTotal')">Money Total</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'moneyTotal'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('cashShop')">
              <span v-text="$t('shopDailyReportApp.assetLive.cashShop')">Cash Shop</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'cashShop'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('cashOwner')">
              <span v-text="$t('shopDailyReportApp.assetLive.cashOwner')">Cash Owner</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'cashOwner'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('bankAccount')">
              <span v-text="$t('shopDailyReportApp.assetLive.bankAccount')">Bank Account</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'bankAccount'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('goods')">
              <span v-text="$t('shopDailyReportApp.assetLive.goods')">Goods</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'goods'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('debitor')">
              <span v-text="$t('shopDailyReportApp.assetLive.debitor')">Debitor</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'debitor'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('longTermAssets')">
              <span v-text="$t('shopDailyReportApp.assetLive.longTermAssets')">Long Term Assets</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'longTermAssets'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('transport')">
              <span v-text="$t('shopDailyReportApp.assetLive.transport')">Transport</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'transport'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('equipment')">
              <span v-text="$t('shopDailyReportApp.assetLive.equipment')">Equipment</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'equipment'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('realEstate')">
              <span v-text="$t('shopDailyReportApp.assetLive.realEstate')">Real Estate</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'realEstate'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('other')">
              <span v-text="$t('shopDailyReportApp.assetLive.other')">Other</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'other'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('code')">
              <span v-text="$t('shopDailyReportApp.assetLive.code')">Code</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'code'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('notes')">
              <span v-text="$t('shopDailyReportApp.assetLive.notes')">Notes</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'notes'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="assetLive in assetLives" :key="assetLive.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'AssetLiveView', params: { assetLiveId: assetLive.id } }">{{ assetLive.id }}</router-link>
            </td>
            <td>{{ assetLive.totalAssets }}</td>
            <td>{{ assetLive.currentAssets }}</td>
            <td>{{ assetLive.moneyTotal }}</td>
            <td>{{ assetLive.cashShop }}</td>
            <td>{{ assetLive.cashOwner }}</td>
            <td>{{ assetLive.bankAccount }}</td>
            <td>{{ assetLive.goods }}</td>
            <td>{{ assetLive.debitor }}</td>
            <td>{{ assetLive.longTermAssets }}</td>
            <td>{{ assetLive.transport }}</td>
            <td>{{ assetLive.equipment }}</td>
            <td>{{ assetLive.realEstate }}</td>
            <td>{{ assetLive.other }}</td>
            <td>{{ assetLive.code }}</td>
            <td>{{ assetLive.notes }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'AssetLiveView', params: { assetLiveId: assetLive.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'AssetLiveEdit', params: { assetLiveId: assetLive.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(assetLive)"
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
        ><span id="shopDailyReportApp.assetLive.delete.question" data-cy="assetLiveDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-assetLive-heading" v-text="$t('shopDailyReportApp.assetLive.delete.question', { id: removeId })">
          Are you sure you want to delete this Asset Live?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-assetLive"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeAssetLive()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="assetLives && assetLives.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./asset-live.component.ts"></script>
