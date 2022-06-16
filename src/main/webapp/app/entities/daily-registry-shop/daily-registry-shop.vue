<template>
  <div>
    <h2 id="page-heading" data-cy="DailyRegistryShopHeading">
      <span v-text="$t('shopDailyReportApp.dailyRegistryShop.home.title')" id="daily-registry-shop-heading">Daily Registry Shops</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('shopDailyReportApp.dailyRegistryShop.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'DailyRegistryShopCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-daily-registry-shop"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('shopDailyReportApp.dailyRegistryShop.home.createLabel')"> Create a new Daily Registry Shop </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && dailyRegistryShops && dailyRegistryShops.length === 0">
      <span v-text="$t('shopDailyReportApp.dailyRegistryShop.home.notFound')">No dailyRegistryShops found</span>
    </div>
    <div class="table-responsive" v-if="dailyRegistryShops && dailyRegistryShops.length > 0">
      <table class="table table-striped" aria-describedby="dailyRegistryShops">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="$t('global.field.id')">ID</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('today')">
              <span v-text="$t('shopDailyReportApp.dailyRegistryShop.today')">Today</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'today'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('sales')">
              <span v-text="$t('shopDailyReportApp.dailyRegistryShop.sales')">Sales</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'sales'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('goods')">
              <span v-text="$t('shopDailyReportApp.dailyRegistryShop.goods')">Goods</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'goods'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('cashShop')">
              <span v-text="$t('shopDailyReportApp.dailyRegistryShop.cashShop')">Cash Shop</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'cashShop'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('click')">
              <span v-text="$t('shopDailyReportApp.dailyRegistryShop.click')">Click</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'click'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('terminal')">
              <span v-text="$t('shopDailyReportApp.dailyRegistryShop.terminal')">Terminal</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'terminal'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('debtReturn')">
              <span v-text="$t('shopDailyReportApp.dailyRegistryShop.debtReturn')">Debt Return</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'debtReturn'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('debtGiven')">
              <span v-text="$t('shopDailyReportApp.dailyRegistryShop.debtGiven')">Debt Given</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'debtGiven'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('expense')">
              <span v-text="$t('shopDailyReportApp.dailyRegistryShop.expense')">Expense</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'expense'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('balance')">
              <span v-text="$t('shopDailyReportApp.dailyRegistryShop.balance')">Balance</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'balance'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('code')">
              <span v-text="$t('shopDailyReportApp.dailyRegistryShop.code')">Code</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'code'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="dailyRegistryShop in dailyRegistryShops" :key="dailyRegistryShop.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'DailyRegistryShopView', params: { dailyRegistryShopId: dailyRegistryShop.id } }">{{
                dailyRegistryShop.id
              }}</router-link>
            </td>
            <td>{{ dailyRegistryShop.today }}</td>
            <td>{{ dailyRegistryShop.sales }}</td>
            <td>{{ dailyRegistryShop.goods }}</td>
            <td>{{ dailyRegistryShop.cashShop }}</td>
            <td>{{ dailyRegistryShop.click }}</td>
            <td>{{ dailyRegistryShop.terminal }}</td>
            <td>{{ dailyRegistryShop.debtReturn }}</td>
            <td>{{ dailyRegistryShop.debtGiven }}</td>
            <td>{{ dailyRegistryShop.expense }}</td>
            <td>{{ dailyRegistryShop.balance }}</td>
            <td>{{ dailyRegistryShop.code }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'DailyRegistryShopView', params: { dailyRegistryShopId: dailyRegistryShop.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'DailyRegistryShopEdit', params: { dailyRegistryShopId: dailyRegistryShop.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(dailyRegistryShop)"
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
          id="shopDailyReportApp.dailyRegistryShop.delete.question"
          data-cy="dailyRegistryShopDeleteDialogHeading"
          v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-dailyRegistryShop-heading" v-text="$t('shopDailyReportApp.dailyRegistryShop.delete.question', { id: removeId })">
          Are you sure you want to delete this Daily Registry Shop?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-dailyRegistryShop"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeDailyRegistryShop()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="dailyRegistryShops && dailyRegistryShops.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./daily-registry-shop.component.ts"></script>
