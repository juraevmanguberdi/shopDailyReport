<template>
  <div>
    <h2 id="page-heading" data-cy="AssetRegistryHeading">
      <span v-text="$t('shopDailyReportApp.assetRegistry.home.title')" id="asset-registry-heading">Asset Registries</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('shopDailyReportApp.assetRegistry.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'AssetRegistryCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-asset-registry"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('shopDailyReportApp.assetRegistry.home.createLabel')"> Create a new Asset Registry </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && assetRegistries && assetRegistries.length === 0">
      <span v-text="$t('shopDailyReportApp.assetRegistry.home.notFound')">No assetRegistries found</span>
    </div>
    <div class="table-responsive" v-if="assetRegistries && assetRegistries.length > 0">
      <table class="table table-striped" aria-describedby="assetRegistries">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="$t('global.field.id')">ID</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('today')">
              <span v-text="$t('shopDailyReportApp.assetRegistry.today')">Today</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'today'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('totalAssets')">
              <span v-text="$t('shopDailyReportApp.assetRegistry.totalAssets')">Total Assets</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'totalAssets'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('currentAssets')">
              <span v-text="$t('shopDailyReportApp.assetRegistry.currentAssets')">Current Assets</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'currentAssets'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('moneyTotal')">
              <span v-text="$t('shopDailyReportApp.assetRegistry.moneyTotal')">Money Total</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'moneyTotal'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('cashShop')">
              <span v-text="$t('shopDailyReportApp.assetRegistry.cashShop')">Cash Shop</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'cashShop'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('cashOwner')">
              <span v-text="$t('shopDailyReportApp.assetRegistry.cashOwner')">Cash Owner</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'cashOwner'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('bankAccount')">
              <span v-text="$t('shopDailyReportApp.assetRegistry.bankAccount')">Bank Account</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'bankAccount'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('goods')">
              <span v-text="$t('shopDailyReportApp.assetRegistry.goods')">Goods</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'goods'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('debitor')">
              <span v-text="$t('shopDailyReportApp.assetRegistry.debitor')">Debitor</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'debitor'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('longTermAssets')">
              <span v-text="$t('shopDailyReportApp.assetRegistry.longTermAssets')">Long Term Assets</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'longTermAssets'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('transport')">
              <span v-text="$t('shopDailyReportApp.assetRegistry.transport')">Transport</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'transport'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('equipment')">
              <span v-text="$t('shopDailyReportApp.assetRegistry.equipment')">Equipment</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'equipment'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('realEstate')">
              <span v-text="$t('shopDailyReportApp.assetRegistry.realEstate')">Real Estate</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'realEstate'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('other')">
              <span v-text="$t('shopDailyReportApp.assetRegistry.other')">Other</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'other'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('code')">
              <span v-text="$t('shopDailyReportApp.assetRegistry.code')">Code</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'code'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('notes')">
              <span v-text="$t('shopDailyReportApp.assetRegistry.notes')">Notes</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'notes'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="assetRegistry in assetRegistries" :key="assetRegistry.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'AssetRegistryView', params: { assetRegistryId: assetRegistry.id } }">{{
                assetRegistry.id
              }}</router-link>
            </td>
            <td>{{ assetRegistry.today }}</td>
            <td>{{ assetRegistry.totalAssets }}</td>
            <td>{{ assetRegistry.currentAssets }}</td>
            <td>{{ assetRegistry.moneyTotal }}</td>
            <td>{{ assetRegistry.cashShop }}</td>
            <td>{{ assetRegistry.cashOwner }}</td>
            <td>{{ assetRegistry.bankAccount }}</td>
            <td>{{ assetRegistry.goods }}</td>
            <td>{{ assetRegistry.debitor }}</td>
            <td>{{ assetRegistry.longTermAssets }}</td>
            <td>{{ assetRegistry.transport }}</td>
            <td>{{ assetRegistry.equipment }}</td>
            <td>{{ assetRegistry.realEstate }}</td>
            <td>{{ assetRegistry.other }}</td>
            <td>{{ assetRegistry.code }}</td>
            <td>{{ assetRegistry.notes }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'AssetRegistryView', params: { assetRegistryId: assetRegistry.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'AssetRegistryEdit', params: { assetRegistryId: assetRegistry.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(assetRegistry)"
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
          id="shopDailyReportApp.assetRegistry.delete.question"
          data-cy="assetRegistryDeleteDialogHeading"
          v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-assetRegistry-heading" v-text="$t('shopDailyReportApp.assetRegistry.delete.question', { id: removeId })">
          Are you sure you want to delete this Asset Registry?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-assetRegistry"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeAssetRegistry()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="assetRegistries && assetRegistries.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./asset-registry.component.ts"></script>
