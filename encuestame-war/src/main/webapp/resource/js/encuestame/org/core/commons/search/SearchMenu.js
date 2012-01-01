dojo.provide("encuestame.org.core.commons.search.SearchMenu");

dojo.require("dijit.form.TextBox");
dojo.require('encuestame.org.core.commons');
dojo.require("encuestame.org.core.shared.utils.Suggest");
dojo.require("encuestame.org.main.EnmeMainLayoutWidget");

/**
 * Search menu widget.
 * This widget only return suggest float window with list of results based on keyword.
 */
dojo.declare(
    "encuestame.org.core.commons.search.SearchMenu",
    [encuestame.org.main.EnmeMainLayoutWidget],{
      /*
       * template.
       */
      templatePath: dojo.moduleUrl("encuestame.org.core.commons.search", "templates/searchMenu.html"),

        /*
         * enable widget on template.
         */
        widgetsInTemplate: true,


        label : "Search",

        /*
         * suggest widget referece.
         */
        textBoxWidget : null,


        placeholder : "Search",

        /*
         * store all items.
         */
        listItems : [],


        searchParam: { limit : 10, keyword : ""},

        /*
         * post create process.
         */
        postCreate: function() {
           this.textBoxWidget = dijit.byId(this._suggest);
           if (this.textBoxWidget) {
               this._searchSuggestSupport();
           }
        },


        _setParams: function(value){
            this.searchParam = value;
        },



        /*
         * hide with fade out the suggest box.
         */
        hide : function(){
            //console.info("HIDE");
            this.listItems = [];
            var fadeArgs = {
                    node: this._suggestItems
            };
            dojo.fadeOut(fadeArgs).play();
            this.clear();
        },

        /*
         *
         */
        clear : function(){
            if(this.textBoxWidget){
                this.selectedItem = null;
                this.textBoxWidget.set("value", "");
            }
            dojo.empty(this._suggestItems);
        },

        _moveUp : function(){

        },

        _moveDown : function(){

        },

        /*
         *
         */
        _searchSuggestSupport : function() {
             dojo.connect(this.textBoxWidget, "onKeyUp", dojo.hitch(this, function(e) {
                 if (dojo.keys.SPACE == e.keyCode || dojo.keys.ENTER == e.keyCode) {
                      this.processSpaceAction();
                 } else if (dojo.keys.ESCAPE == e.keyCode) {
                     this.hide();
                 } else if (dojo.keys.UP_ARROW == e.keyCode) {

                 } else if (dojo.keys.DOWN_ARROW == e.keyCode) {

                 } else {
                     this._setParams(
                             { limit: 10,
                               keyword : this.textBoxWidget.get("value"),
                               excludes : this.exclude});
                     console.debug("suggest", this.textBoxWidget.get("value"));
                     if (this.textBoxWidget.get("value") != "") {
                         this._searchCallService();
                     }
                 }
                 //this.textBoxWidget //TODO: this.hide() on lost focus.
             }));
        },

        /*
         * Make a call to search service.
         * {"error":{},"success":{"items":{"profiles":[],"questions":[],"attachments":[],"tags":[{"id":null,"hits":3000001,"typeSearchResult":"HASHTAG","urlLocation":"/hashtag/nicaragua","score":100,"itemSearchTitle":"Nicaragua","itemSearchDescription":null}]},"label":"itemSearchTitle","identifier":"id"}}
         */
        _searchCallService : function(){
            var load = dojo.hitch(this, function(data){
                console.debug("social _searchCallService", data);
                dojo.empty(this._suggestItems);
                if("items" in data.success) {
                    var fadeArgs = {
                            node: this._suggestItems
                    };
                    dojo.fadeIn(fadeArgs).play();

                    this.printItems(data.success.items);
                }
            });
            var error = function(error) {
                console.debug("error", error);
            };
            encuestame.service.xhrGet(
                    encuestame.service.search.suggest, this.searchParam, load, error);
        },

        /*
         * Create a list of item.
         * @param data suggested search item.
         */
        printItems : function(data) {
            var widget = new encuestame.org.core.commons.search.SearchSuggestItemsByType(
                    {
                     data : data,
                     parentWidget : this
                     });
            this.listItems.push(widget);
            this._suggestItems.appendChild(widget.domNode);
        }
});

/*
 * Widget define item suggest box.
 */
dojo.declare(
        "encuestame.org.core.commons.search.SearchSuggestItemsByType",
        [encuestame.org.main.EnmeMainLayoutWidget],{
        //template
        templatePath: dojo.moduleUrl("encuestame.org.core.commons.search", "templates/searchSuggestItem.html"),
        //widgets in template
        wigetsInTemplate: true,
        //reference of suggest widget.
        parentWidget: null,

        data: null,

        postCreate : function() {
            //console.info("SearchSuggestItem", this.data);
            if (this.data) {
                if ("tags" in this.data) {
                    this._printItems("Hashtags", this.data.tags);
                };

                if ("profiles" in this.data) {
                    this._printItems("Profiles", this.data.profiles);
                };

                if ("questions" in this.data) {
                    this._printItems("Questions", this.data.questions);
                };

                if ("attachments" in this.data) {
                    this._printItems("Documents", this.data.attachments);
                };
            }
        },

        /*
        *
        */
       _printItems : function(label, items) {
           console.info("_printHashtags", items);
           if (items.length > 0) {
               var hash = new encuestame.org.core.commons.search.SearchSuggestItemSection({label : label, items : items});
               this._container.appendChild(hash.domNode);
           }
       }
});

dojo.declare(
        "encuestame.org.core.commons.search.SearchSuggestItemSection",
        [encuestame.org.main.EnmeMainLayoutWidget],{

        //template
        templatePath: dojo.moduleUrl("encuestame.org.core.commons.search", "templates/searchSuggestItemSection.html"),

        //
        items : [],

        label : "",

        postCreate : function() {
            dojo.forEach(this.items,
                    dojo.hitch(this,function(item) {
                 this._itemSuggest.appendChild(this._createItem(item));
            }));
        },

        /*
         *
         */
        _createItem : function(item) {
            var div = dojo.create("div");
            dojo.addClass(div, "web-search-item");
            var h4 = dojo.create("h4", null, div);
            h4.innerHTML = item.itemSearchTitle;
            if (item.urlLocation != "" && item.urlLocation != null) { //on click point to this url.
               dojo.connect(div, "onclick", dojo.hitch(this, function(event) {
                   console.debug("click item", encuestame.contextDefault+item.urlLocation	);
                   document.location.href = encuestame.contextDefault+item.urlLocation;
               }));

               dojo.connect(div, "onKeyUp", dojo.hitch( this, function(e) {
                    if (dojo.keys.ENTER == e.keyCode) {
                        console.info("_createItem key up", item.urlLocation);
                    }
               }));
            } else { // point to search url

            }
            return div;
        }
});