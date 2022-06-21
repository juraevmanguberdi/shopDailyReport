<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="shopDailyReportApp.requiredProduct.home.createOrEditLabel"
          data-cy="RequiredProductCreateUpdateHeading"
          v-text="$t('shopDailyReportApp.requiredProduct.home.createOrEditLabel')"
        >
          Create or edit a RequiredProduct
        </h2>
        <div>
          <div class="form-group" v-if="requiredProduct.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="requiredProduct.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.requiredProduct.name')" for="required-product-name"
              >Name</label
            >
            <input
              type="text"
              class="form-control"
              name="name"
              id="required-product-name"
              data-cy="name"
              :class="{ valid: !$v.requiredProduct.name.$invalid, invalid: $v.requiredProduct.name.$invalid }"
              v-model="$v.requiredProduct.name.$model"
              required
            />
            <div v-if="$v.requiredProduct.name.$anyDirty && $v.requiredProduct.name.$invalid">
              <small class="form-text text-danger" v-if="!$v.requiredProduct.name.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.requiredProduct.note')" for="required-product-note"
              >Note</label
            >
            <input
              type="text"
              class="form-control"
              name="note"
              id="required-product-note"
              data-cy="note"
              :class="{ valid: !$v.requiredProduct.note.$invalid, invalid: $v.requiredProduct.note.$invalid }"
              v-model="$v.requiredProduct.note.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('shopDailyReportApp.requiredProduct.code')" for="required-product-code"
              >Code</label
            >
            <input
              type="text"
              class="form-control"
              name="code"
              id="required-product-code"
              data-cy="code"
              :class="{ valid: !$v.requiredProduct.code.$invalid, invalid: $v.requiredProduct.code.$invalid }"
              v-model="$v.requiredProduct.code.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('shopDailyReportApp.requiredProduct.requiredProductType')"
              for="required-product-requiredProductType"
              >Required Product Type</label
            >
            <select
              class="form-control"
              id="required-product-requiredProductType"
              data-cy="requiredProductType"
              name="requiredProductType"
              v-model="requiredProduct.requiredProductType"
              required
            >
              <option v-if="!requiredProduct.requiredProductType" v-bind:value="null" selected></option>
              <option
                v-bind:value="
                  requiredProduct.requiredProductType && requiredProductTypeOption.id === requiredProduct.requiredProductType.id
                    ? requiredProduct.requiredProductType
                    : requiredProductTypeOption
                "
                v-for="requiredProductTypeOption in requiredProductTypes"
                :key="requiredProductTypeOption.id"
              >
                {{ requiredProductTypeOption.id }}
              </option>
            </select>
          </div>
          <div v-if="$v.requiredProduct.requiredProductType.$anyDirty && $v.requiredProduct.requiredProductType.$invalid">
            <small
              class="form-text text-danger"
              v-if="!$v.requiredProduct.requiredProductType.required"
              v-text="$t('entity.validation.required')"
            >
              This field is required.
            </small>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.requiredProduct.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./required-product-update.component.ts"></script>
